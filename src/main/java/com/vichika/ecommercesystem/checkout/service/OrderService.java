package com.vichika.ecommercesystem.checkout.service;

import com.vichika.ecommercesystem.checkout.dto.request.CheckoutRequest;
import com.vichika.ecommercesystem.checkout.dto.response.OrderResponse;
import java.util.List;

public interface OrderService {
    OrderResponse checkout(CheckoutRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);

    OrderResponse cancelOrder(Long orderId);
}
