package com.vichika.ecommercesystem.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PermissionRequest(
        @NotBlank(message = "Permission name is required")
        @Size(max = 100, message = "Permission name must not exceed 100 characters")
        String name
) {
}
