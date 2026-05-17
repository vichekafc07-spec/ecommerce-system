package com.vichika.ecommercesystem.product.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @NotBlank(message = "Code is required")
        @Size(max = 50, message = "Code must not exceed 50 characters")
        String code,

        @NotNull(message = "Category ID is required")
        @Positive(message = "Category ID must be positive")
        Byte categoryId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        Integer quantity,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Invalid price format")
        BigDecimal price,

        @DecimalMin(value = "0.0", message = "Discount cannot be negative")
        @Digits(integer = 10, fraction = 2, message = "Invalid discount format")
        BigDecimal discount
) {
}
