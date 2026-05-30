package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.checkout.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserOrderByCreatedAtDesc(AppUser user);

    Optional<Order> findByIdAndUser(Long id, AppUser user);
}
