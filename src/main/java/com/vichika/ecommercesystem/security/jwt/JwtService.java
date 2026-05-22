package com.vichika.ecommercesystem.security.jwt;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.auth.model.Permission;
import com.vichika.ecommercesystem.auth.model.Role;
import com.vichika.ecommercesystem.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
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

    public String generateRefreshToken(AppUser user){
        return generateToken(user,jwtConfig.getRefreshTokenExp());
    }

    public String parseToken(String token){
        try {
            var claims = getClaims(token);
            return Jwts.builder()
                    .claims(claims)
                    .signWith(jwtConfig.getKey())
                    .compact();
        }catch (JwtException e){
            return null;
        }
    }

    public boolean isExpiration(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    public String getUsername(String token){
        return getClaims(token).getSubject();
    }

    public List<String> getRole(String token){
        return getClaims(token).get("role", List.class);
    }

    public List<String> getPermission(String token){
        return getClaims(token).get("permission", List.class);
    }

    private String generateToken(AppUser user, long tokenExpiration) {
        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        List<String> permissions = user.getRoles()
                .stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Permission::getName)
                .distinct()
                .toList();

        var claims = Jwts.claims()
                .subject(user.getUsername())
                .add("email", user.getEmail())
                .add("role", roles)
                .add("permission", permissions)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(jwtConfig.getKey())
                .compact();
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(jwtConfig.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
