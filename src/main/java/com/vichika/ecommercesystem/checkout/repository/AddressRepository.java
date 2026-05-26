package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.checkout.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUser(AppUser user);

    Optional<Address> findByIdAndUser(Long id, AppUser user);
}
