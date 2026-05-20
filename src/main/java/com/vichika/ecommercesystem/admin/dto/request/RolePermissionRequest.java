package com.vichika.ecommercesystem.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record RolePermissionRequest(
        @NotNull(message = "permissionIds must not be null")
        @NotEmpty(message = "permissionIds must not be empty")
        Set<Integer> permissionIds
) {
}
