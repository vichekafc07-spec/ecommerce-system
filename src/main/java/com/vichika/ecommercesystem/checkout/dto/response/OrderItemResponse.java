package com.vichika.ecommercesystem.checkout.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        BigDecimal productPrice,
        Integer quantity,
        BigDecimal subTotal
) {
}
