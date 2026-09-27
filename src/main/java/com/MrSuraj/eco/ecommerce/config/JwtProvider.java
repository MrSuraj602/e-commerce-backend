package com.MrSuraj.eco.ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class JwtProvider {
    private final SecretKey key;

    public JwtProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication auth){
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+846000000))
                .claim("email",auth.getName())
                .claim("authorities", auth.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.joining(",")))
                .signWith(key).compact();

        return jwt;
    }

    public String getEmailFromToken(String jwt){
        return getClaimsFromToken(jwt).get("email", String.class);
    }

    public Claims getClaimsFromToken(String token) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
    }
}
