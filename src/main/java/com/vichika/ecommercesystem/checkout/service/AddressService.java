package com.vichika.ecommercesystem.checkout.service;

import com.vichika.ecommercesystem.checkout.dto.request.AddressRequest;
import com.vichika.ecommercesystem.checkout.dto.response.AddressResponse;

public interface AddressService {

    AddressResponse createAddress(AddressRequest request);
}
