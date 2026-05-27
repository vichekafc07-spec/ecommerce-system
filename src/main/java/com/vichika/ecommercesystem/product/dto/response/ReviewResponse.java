package com.vichika.ecommercesystem.product.dto.response;

public record ReviewResponse(
        Long id,
        Integer rating,
        String comment,
        Long userId,
        String userName,
        Long productName
) {
}
