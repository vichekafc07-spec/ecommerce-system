package com.vichika.ecommercesystem.checkout.service;

import com.vichika.ecommercesystem.checkout.dto.OrderResponse;
import java.util.List;

public interface OrderService {
    OrderResponse checkout();

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);

    OrderResponse cancelOrder(Long orderId);
}
