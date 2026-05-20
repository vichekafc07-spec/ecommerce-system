package com.vichika.ecommercesystem.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record UserRoleRequest(
        @NotNull(message = "roleIds must not be null")
        @NotEmpty(message = "roleIds must not be empty")
        Set<Integer> roleIds
) {
}
