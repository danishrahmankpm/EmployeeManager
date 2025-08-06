package com.example.EmployeeManager.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    // Strong 256-bit secret key (store this securely, e.g., as an environment variable)
    private static final String SECRET = "$2a$12$o3PuSTj2U0jkgcjt90FR/OsCvVs2W4U.XuOG.yguI3Etg2iLIpLnC";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Token validity: 30 minutes
    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 30;

    // Generate JWT
    public String generateToken(UUID userId, String role) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role)
                .setIssuer("employee-manager")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT (throws exception if invalid or expired)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Extract user ID (subject) from JWT
    public String extractUserId(String token) {
        return getClaims(token).getSubject();
    }

    // Extract role from JWT
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
