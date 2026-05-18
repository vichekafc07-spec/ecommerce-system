package com.vichika.ecommercesystem.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserChangePassword(
        @NotBlank(message = "Old password is required")
        @Size(min = 8, max = 100, message = "Old password must be at least 8 characters")
        String oldPassword,

        @NotBlank(message = "New password is required")
        @Size(min = 8, max = 100, message = "New password must be at least 8 characters")
        String newPassword
) {
}
