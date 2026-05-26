package com.vichika.ecommercesystem.checkout.mapper;

import com.vichika.ecommercesystem.checkout.dto.response.AddressResponse;
import com.vichika.ecommercesystem.checkout.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse toAddressResponse(Address address);
}
