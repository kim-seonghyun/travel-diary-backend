package com.ssafy.trip.utils;

import com.ssafy.trip.user.dto.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key key;
    private final Long expiration;
    private final Long refreshExpiration;
    private final Long resetExpiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") Long expiration,
                   @Value("${jwt.refreshExpiration}") Long refreshExpiration,
                   @Value("${jwt.resetExpiration}") Long resetExpiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
        this.refreshExpiration = refreshExpiration;
        this.resetExpiration = resetExpiration;
    }

    // 엑세스 토큰을 발급한다.
    public String generateAccessToken(UserResponse userResponse, List<String> roles) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("userId", userResponse.getId());
        claim.put("userName", userResponse.getName());
        claim.put("roles", roles);

        return Jwts.builder()
                .setSubject(userResponse.getName())
                .setIssuedAt(new Date())
                .setClaims(claim)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 리셋 토큰을 발급한다.
    public String generateResetToken(String email) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("email", email);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setClaims(claim)
                .setExpiration(new Date(System.currentTimeMillis() + resetExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레시 토큰을 발급한다.
    public String generateRefreshToken(UserResponse userResponse, List<String> roles) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("userId", userResponse.getId());
        claim.put("userName", userResponse.getName());
        claim.put("roles", roles);

        return Jwts.builder()
                .setSubject(userResponse.getName())
                .setIssuedAt(new Date())
                .setClaims(claim)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰을 파싱하여 클레임 추출함.
    public Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }
}
