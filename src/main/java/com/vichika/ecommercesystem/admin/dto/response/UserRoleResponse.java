package com.vichika.ecommercesystem.admin.dto.response;

import java.util.Set;

public record UserRoleResponse(
        Long userId,
        Set<String> roles
) {
}
