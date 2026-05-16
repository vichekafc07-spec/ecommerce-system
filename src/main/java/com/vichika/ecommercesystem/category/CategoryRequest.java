package com.vichika.ecommercesystem.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "category name required")
        @Size(min = 3,max = 50, message = "category name must be at least 3 characters and max 50 characters")
        String name,
        @NotBlank(message = "category code required")
        String code) {
}
