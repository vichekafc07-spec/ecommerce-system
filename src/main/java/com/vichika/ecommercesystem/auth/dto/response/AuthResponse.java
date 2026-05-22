package com.vichika.ecommercesystem.auth.dto.response;

import java.util.Set;

public record AuthResponse(
        Long id,
        String username,
        String email,
        Set<String> roles
) {
}
