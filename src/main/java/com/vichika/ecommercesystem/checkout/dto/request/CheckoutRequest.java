package com.vichika.ecommercesystem.checkout.dto.request;

import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
        @NotNull(message = "Address id is required")
        Long addressId
) {
}
