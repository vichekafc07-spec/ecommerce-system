package com.vichika.ecommercesystem.checkout;

import com.vichika.ecommercesystem.checkout.dto.request.CheckoutRequest;
import com.vichika.ecommercesystem.checkout.dto.response.OrderResponse;
import com.vichika.ecommercesystem.checkout.service.OrderService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<APIResponse<OrderResponse>> checkout(@Valid @RequestBody CheckoutRequest request){
        return ResponseEntity.ok(APIResponse.create(orderService.checkout(request)));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<APIResponse<List<OrderResponse>>> getMyOrder(){
        return ResponseEntity.ok(APIResponse.ok(orderService.getMyOrders()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<APIResponse<OrderResponse>> getOrderById(@PathVariable Long orderId){
        return ResponseEntity.ok(APIResponse.ok(orderService.getOrderById(orderId)));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<APIResponse<OrderResponse>> cancelOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(APIResponse.ok(orderService.cancelOrder(orderId)));
    }

}
