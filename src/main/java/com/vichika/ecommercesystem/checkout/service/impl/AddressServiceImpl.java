package com.vichika.ecommercesystem.checkout.service.impl;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.checkout.dto.request.AddressRequest;
import com.vichika.ecommercesystem.checkout.dto.response.AddressResponse;
import com.vichika.ecommercesystem.checkout.mapper.AddressMapper;
import com.vichika.ecommercesystem.checkout.model.Address;
import com.vichika.ecommercesystem.checkout.repository.AddressRepository;
import com.vichika.ecommercesystem.checkout.service.AddressService;
import com.vichika.ecommercesystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AuthUtil authUtil;
    private final AddressMapper addressMapper;

    @Override
    public AddressResponse createAddress(AddressRequest request) {

        var user = authUtil.getCurrentUser();
        if (Boolean.TRUE.equals(request.isDefault())){
            clearDefaultAddresses(user);
        }

        var address = Address.builder()
                .fullName(request.fullName())
                .phoneNumber(request.phoneNumber())
                .province(request.province())
                .city(request.city())
                .street(request.street())
                .isDefault(request.isDefault())
                .user(user)
                .build();

        return addressMapper.toAddressResponse(addressRepository.save(address));
    }

    private void clearDefaultAddresses(AppUser user){
        List<Address> addresses = addressRepository.findByUser(user);
        addresses.forEach(address -> address.setIsDefault(false));
        addressRepository.saveAll(addresses);
    }
}
