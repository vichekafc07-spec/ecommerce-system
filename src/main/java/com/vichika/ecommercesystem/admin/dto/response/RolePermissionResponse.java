package com.vichika.ecommercesystem.admin.dto.response;

import java.util.Set;

public record RolePermissionResponse(
        Integer roleId,
        Set<String> permissions
) {
}
