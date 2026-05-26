package com.vichika.ecommercesystem.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String code,
        String categoryName,
        Integer quantity,
        String imageUrl,
        BigDecimal price,
        BigDecimal discount,
        BigDecimal finalPrice
) {
}
