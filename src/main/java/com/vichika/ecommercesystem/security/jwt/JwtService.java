package com.vichika.ecommercesystem.security.jwt;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.auth.model.Role;
import com.vichika.ecommercesystem.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public String generateAccessToken(AppUser user){
        return generateToken(user,jwtConfig.getAccessTokenExp());
    }

    public String generateToken(AppUser user, long tokenExpiration) {
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        var claims = Jwts.claims()
                .subject(user.getUsername())
                .add("email", user.getEmail())
                .add("role", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(jwtConfig.getKey())
                .compact();
    }
}
