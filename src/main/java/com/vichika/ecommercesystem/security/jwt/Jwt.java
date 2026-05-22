package com.vichika.ecommercesystem.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public boolean isExpired(){
        return claims.getExpiration().before(new Date());
    }

    public String getUsername(){
        return claims.getSubject();
    }

    public List<String> getRole(){
        return claims.get("role", List.class);
    }

    public List<String> getPermission(){
        return claims.get("permission", List.class);
    }

    public String generate(){
        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

}
