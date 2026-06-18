package com.banking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //  Generate Token with userId
    public String generateToken(String email, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);  // ✅ store userId in token

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Email from Token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // UserId from Token
    public Long extractUserId(String token) {
        return ((Number) getClaims(token)
                .get("userId")).longValue();
    }

    //  Validate Token
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}