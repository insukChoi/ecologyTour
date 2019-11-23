package com.insuk.ecologytour.service;

import com.insuk.ecologytour.domain.entity.UserInfo;
import com.insuk.ecologytour.repository.UserRepository;
import com.insuk.ecologytour.web.request.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo savedUserInfo = userRepository.findByUserId(userName).orElseThrow(() -> new UsernameNotFoundException("Not Found User"));
        return new User(savedUserInfo.getUserId(), savedUserInfo.getPassword(), new ArrayList<>());
    }

    public UserDetails saveUser(JwtRequest jwtRequest){
        UserInfo savedUserInfo = new UserInfo(
                jwtRequest.getUsername(),
                bcryptEncoder.encode(jwtRequest.getPassword())
        );
        userRepository.save(savedUserInfo);

        return new User(savedUserInfo.getUserId(), savedUserInfo.getPassword(), new ArrayList<>());
    }
}
