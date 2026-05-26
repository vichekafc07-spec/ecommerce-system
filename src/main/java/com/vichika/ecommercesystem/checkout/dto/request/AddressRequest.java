package com.vichika.ecommercesystem.checkout.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequest(

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Province is required")
        String province,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Street is required")
        String street,

        @NotNull(message = "Default status is required")
        Boolean isDefault
) {
}
