package com.vichika.ecommercesystem.checkout;

import com.vichika.ecommercesystem.checkout.dto.OrderResponse;
import com.vichika.ecommercesystem.checkout.service.OrderService;
import com.vichika.ecommercesystem.common.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<APIResponse<OrderResponse>> checkout(){
        return ResponseEntity.ok(APIResponse.create(orderService.checkout()));
    }

}
