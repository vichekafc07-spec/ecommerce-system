package com.vichika.ecommercesystem.audit.dto;

import java.math.BigDecimal;

public record ProductAuditDto(
        Long id,
        String name,
        String code,
        String description,
        Integer quantity,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal totalPrice,
        String categoryName,
        Byte categoryId
) {}
