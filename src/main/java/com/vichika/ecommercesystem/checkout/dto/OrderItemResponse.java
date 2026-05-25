package com.vichika.ecommercesystem.checkout.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        BigDecimal productPrice,
        Integer quantity,
        BigDecimal finalPrice
) {
}
