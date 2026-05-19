package com.vichika.ecommercesystem.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {
    @Value("${spring.jwt.secretKey}")
    private String secretKey;
    @Value("${spring.jwt.accessTokenExp}")
    private Long accessToken;

    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * accessToken))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
