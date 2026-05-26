package com.vichika.ecommercesystem.checkout.dto.response;

import com.vichika.ecommercesystem.checkout.model.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long orderId,
        String orderNumber,
        BigDecimal totalAmount,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {
}