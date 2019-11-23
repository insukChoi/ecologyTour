package com.insuk.ecologytour.controller;

import com.insuk.ecologytour.common.JwtTokenUtil;
import com.insuk.ecologytour.domain.exception.InvalidCredentialsException;
import com.insuk.ecologytour.domain.exception.UnauthorizedException;
import com.insuk.ecologytour.domain.exception.UserDisabledException;
import com.insuk.ecologytour.service.JwtUserDetailsService;
import com.insuk.ecologytour.web.request.JwtRequest;
import com.insuk.ecologytour.web.response.JwtResponse;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(description = "회원 인증 API")
@Slf4j
@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @ApiOperation(value = "Sign UP to 토큰발급")
    @PostMapping(value = "/signup")
    public JwtResponse signup(@RequestBody JwtRequest authenticationRequest){
        final UserDetails userDetails = userDetailsService.saveUser(authenticationRequest);
        final String accessToken = jwtTokenUtil.generateToken(userDetails, "jwt");
        final String refreshToken = jwtTokenUtil.generateToken(userDetails, "refresh");

        return new JwtResponse(accessToken, refreshToken);
    }

    @ApiOperation(value = "Sign IN to 토큰발급")
    @PostMapping(value = "/signin")
    public JwtResponse signin(@RequestBody JwtRequest authenticationRequest){
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String accessToken = jwtTokenUtil.generateToken(userDetails, "jwt");
        final String refreshToken = jwtTokenUtil.generateToken(userDetails, "refresh");

        return new JwtResponse(accessToken, refreshToken);
    }

    @ApiOperation(value = "토큰 재발급")
    @PostMapping(value = "/refreshToken")
    public JwtResponse refreshToken(HttpServletRequest request){
        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        }
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
        if (!"refresh".equals(claims.get("type"))){
            throw new UnauthorizedException("token is not refresh token");
        }

       jwtToken = jwtTokenUtil.generateToken(claims.getSubject(), "jwt");
        final String refreshToken = jwtTokenUtil.generateToken(claims.getSubject(), "refresh");

        return new JwtResponse(jwtToken, refreshToken);
    }


    private void authenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UserDisabledException("USER DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("INVALID CREDENTIALS", e);
        }
    }

}
