package com.vichika.ecommercesystem.cart.dto.response;

import java.math.BigDecimal;

public record CartItemResponse(
        Long itemId,
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal finalPrice
) {
}
