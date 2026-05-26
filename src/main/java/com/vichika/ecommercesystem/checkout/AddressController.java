package com.vichika.ecommercesystem.checkout;

import com.vichika.ecommercesystem.checkout.dto.request.AddressRequest;
import com.vichika.ecommercesystem.checkout.dto.response.AddressResponse;
import com.vichika.ecommercesystem.checkout.service.AddressService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<APIResponse<AddressResponse>> create(@Valid @RequestBody AddressRequest request){
        return ResponseEntity.ok(APIResponse.create(addressService.createAddress(request)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<AddressResponse>>> getMyAddresses(){
        return ResponseEntity.ok(APIResponse.ok(addressService.getMyAddresses()));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<APIResponse<AddressResponse>> update(@PathVariable Long addressId,
                                                               @Valid @RequestBody AddressRequest request){
        return ResponseEntity.ok(APIResponse.ok(addressService.updateAddress(addressId,request)));
    }

    @PatchMapping("/default/{addressId}")
    public ResponseEntity<APIResponse<AddressResponse>> setDefault(@PathVariable Long addressId){
        return ResponseEntity.ok(APIResponse.ok(addressService.setDefault(addressId)));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> delete(@PathVariable Long addressId){
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

}
