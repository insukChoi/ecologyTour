package com.insuk.ecologytour.service;

import com.insuk.ecologytour.domain.entity.UserInfo;
import com.insuk.ecologytour.repository.UserRepository;
import com.insuk.ecologytour.web.request.JwtRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtUserDetailsServiceTest {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @MockBean
    private UserRepository userRepository;

    private UserInfo user_1;
    private UserInfo user_2;
    private final String password = "123";

    @Before
    public void init() throws RuntimeException{
        user_1 = new UserInfo("insuk", password);
        user_2 = new UserInfo("heera", password);
    }

    @Test
    public void 패스워드_인코딩_비교_테스트(){
        user_1.setPassword(user_1.getPassword());
        user_2.setPassword(bcryptEncoder.encode(user_2.getPassword()));
        assertThat(bcryptEncoder.matches(user_1.getPassword(), user_2.getPassword()), is(true));
    }

    @Test
    public void 유저_저장_및_조회_테스트(){
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(user_1.getUserId());
        jwtRequest.setPassword(user_1.getPassword());

        UserDetails savedUserDetails = jwtUserDetailsService.saveUser(jwtRequest);

        Mockito.when(userRepository.findByUserId(savedUserDetails.getUsername()))
                .thenReturn(java.util.Optional.of(user_1));

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(savedUserDetails.getUsername());

        assertThat(userDetails.getUsername(), is(user_1.getUserId()));
        assertThat(userDetails.getPassword(), is(user_1.getPassword()));
    }
}