package com.vichika.ecommercesystem.auth.dto.request;

public record ChangePassword(
        String oldPassword,
        String newPassword
) {
}
