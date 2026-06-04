package com.vichika.ecommercesystem.admin.dto.response;

public record TopProductResponse(
        Long productId,
        String productName,
        Long soldQuantity
) {
}
