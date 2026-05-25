package com.vichika.ecommercesystem.checkout.service;

import com.vichika.ecommercesystem.cart.repository.CartItemRepository;
import com.vichika.ecommercesystem.cart.repository.CartRepository;
import com.vichika.ecommercesystem.checkout.OrderMapper;
import com.vichika.ecommercesystem.checkout.dto.OrderResponse;
import com.vichika.ecommercesystem.checkout.repository.OrderItemRepository;
import com.vichika.ecommercesystem.checkout.repository.OrderRepository;
import com.vichika.ecommercesystem.product.ProductRepository;
import com.vichika.ecommercesystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final AuthUtil authUtil;

    @Override
    public OrderResponse checkout() {

        return null;
    }
}
