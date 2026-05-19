package com.vichika.ecommercesystem.config;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
@Getter
@Setter
public class JwtConfig {
    private String secretKey;
    private Long accessTokenExp;
    private Long refreshTokenExp;

    public SecretKey getKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
