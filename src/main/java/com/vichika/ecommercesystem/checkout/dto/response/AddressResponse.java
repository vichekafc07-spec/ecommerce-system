package com.vichika.ecommercesystem.checkout.dto.response;

public record AddressResponse(
        Long id,
        String fullName,
        String phoneNumber,
        String province,
        String city,
        String street,
        Boolean isDefault
) {
}
