package com.vichika.ecommercesystem.checkout;

import com.vichika.ecommercesystem.checkout.dto.request.AddressRequest;
import com.vichika.ecommercesystem.checkout.dto.response.AddressResponse;
import com.vichika.ecommercesystem.checkout.service.AddressService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<APIResponse<AddressResponse>> create(@Valid @RequestBody AddressRequest request){
        return ResponseEntity.ok(APIResponse.create(addressService.createAddress(request)));
    }

}
