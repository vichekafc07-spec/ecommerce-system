package com.vichika.ecommercesystem.product.dto.request;

import jakarta.validation.constraints.*;

public record ReviewRequest(

        @NotNull(message = "Product ID is required")
        @Positive(message = "Product ID must be positive")
        Long productId,

        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Minimum rating is 1")
        @Max(value = 5, message = "Maximum rating is 5")
        Integer rating,

        @NotBlank(message = "Comment is required")
        String comment
) {
}
