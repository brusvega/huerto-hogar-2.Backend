package com.example.BackEndHuerto.security;

import com.example.BackEndHuerto.model.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 horas
    private static final String SECRET_KEY = "12345678901234567890123456789012"; // 32 chars mínimo

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generar token ------------------------------------
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("rol", "ROLE_" + usuario.getRol().toUpperCase())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extraer email -------------------------------------
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // Extraer cualquier claim
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validar token -------------------------------------
    public boolean isTokenValid(String token, String email) {
        String usuarioEmail = extractEmail(token);
        return usuarioEmail.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
