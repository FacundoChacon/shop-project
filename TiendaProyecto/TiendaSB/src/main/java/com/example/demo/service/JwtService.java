package com.example.demo.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String extraerRol(String token){

        return obtenerClaims(token)
                .get("rol", String.class);
    }

    public String generarToken(
            String username,
            String rol){

        return Jwts.builder()
                .setSubject(username)
                .claim("rol",rol)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis() + expiration
                        )
                )
                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String extraerUsername(String token){
        return obtenerClaims(token)
                .getSubject();
    }

    public boolean validarToken(String token){
        try{

            obtenerClaims(token);
            return true;

        }catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            return false;
        }
    }

    private Claims obtenerClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}