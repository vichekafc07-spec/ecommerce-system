package com.vichika.ecommercesystem.auth.dto.response;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}
