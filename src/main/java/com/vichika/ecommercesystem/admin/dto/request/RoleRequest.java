package com.vichika.ecommercesystem.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RoleRequest(
        @NotBlank(message = "Role name must not be blank")
        @Size(min = 2, max = 50)
        @Pattern(regexp = "^[A-Za-z_]+$", message = "Role name can only contain letters and underscores")
        String name
) {
}
