package com.vichika.ecommercesystem.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequest(
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be greater than 0")
        Integer quantity
) {
}
