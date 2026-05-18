package com.vichika.ecommercesystem.auth.dto.request;

public record UserUpdateRequest(
        String username,
        String email
) {
}
