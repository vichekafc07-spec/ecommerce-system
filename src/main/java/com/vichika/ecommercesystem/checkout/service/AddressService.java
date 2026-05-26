package com.vichika.ecommercesystem.checkout.service;

import com.vichika.ecommercesystem.checkout.dto.request.AddressRequest;
import com.vichika.ecommercesystem.checkout.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse createAddress(AddressRequest request);

    List<AddressResponse> getMyAddresses();

    AddressResponse updateAddress(Long addressId, AddressRequest request);
}
