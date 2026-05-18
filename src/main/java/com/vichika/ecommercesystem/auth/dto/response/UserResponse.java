package com.vichika.ecommercesystem.auth.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email
) {
}
