package com.eorganization.portifolio.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Date;

@Service
public class JwtService {

    private final Key key;
    private final long accessValiditySeconds;
    private final long refreshValiditySeconds;

    public JwtService(@Value("${jwt.secret}") String secret, @Value("${jwt.accessTokenValiditySeconds}") long accessValiditySeconds,
            @Value("${jwt.refreshTokenValiditySeconds}") long refreshValiditySeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessValiditySeconds = accessValiditySeconds;
        this.refreshValiditySeconds = refreshValiditySeconds;
    }

    public String generateAccessToken(String nomUsuario, Collection<String> perfis) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessValiditySeconds * 1000);

        return Jwts.builder().setSubject(nomUsuario).claim("roles", perfis).setIssuedAt(now).setExpiration(exp).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(String nomUsuario) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshValiditySeconds * 1000);

        return Jwts.builder().setSubject(nomUsuario).setIssuedAt(now).setExpiration(exp).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRolesProfiles(String token) {
        var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        Object rolesProfiles = claims.get("roles");
        
        if (rolesProfiles instanceof Collection) {
            return ((Collection<?>) rolesProfiles).stream().map(Object::toString).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
