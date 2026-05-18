package com.eorganization.portifolio.security;

import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final Key key;
    private final long accessValiditySeconds;
    private final long refreshValiditySeconds;

    public JwtService(@Value("${jwt.secret}") String secret,
            @Value("${jwt.accessTokenValiditySeconds}") long accessValiditySeconds,
            @Value("${jwt.refreshTokenValiditySeconds}") long refreshValiditySeconds) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.accessValiditySeconds = accessValiditySeconds;
        this.refreshValiditySeconds = refreshValiditySeconds;
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return "access".equals(extractAllClaims(token).get("type"));
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Object roles = extractAllClaims(token).get("roles");
        if (roles instanceof Collection) {
            return ((Collection<?>) roles).stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public String generateAccessToken(String nomUsuario, Collection<String> perfis) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessValiditySeconds * 1000);

        return Jwts.builder()
                .setSubject(nomUsuario)
                .claim("type", "access")
                .claim("roles", perfis)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key).compact();
    }

    public String generateRefreshToken(String nomUsuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshValiditySeconds * 1000);

        return Jwts.builder()
                .setSubject(nomUsuario)
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key).compact();
    }

}
