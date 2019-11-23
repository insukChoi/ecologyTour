package com.insuk.ecologytour.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.ttl}")
    private long jwtTtl;

    @Value("${refresh.ttl}")
    private long refreshTtl;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // secret key 로 토큰 검색
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // 토큰 만료 체크
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user by userDetails
    public String generateToken(UserDetails userDetails,  String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", type);
        return doGenerateToken(claims, userDetails.getUsername(), type);
    }

    // generate token for user by userName
    public String generateToken(String userName,  String type) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", type);
        return doGenerateToken(claims, userName, type);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String type) {
        long tokenTTL = "jwt".equals(type) ? jwtTtl : refreshTtl;
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenTTL))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // 토큰 검증
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
