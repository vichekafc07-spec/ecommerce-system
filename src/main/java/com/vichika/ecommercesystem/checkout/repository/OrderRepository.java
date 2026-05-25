package com.vichika.ecommercesystem.checkout.repository;

import com.vichika.ecommercesystem.checkout.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
