package com.vichika.ecommercesystem.audit.dto;

public record UserAuditDto(
        Long id,
        String name,
        String email
) {
}
