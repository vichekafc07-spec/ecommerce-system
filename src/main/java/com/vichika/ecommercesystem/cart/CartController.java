package com.vichika.ecommercesystem.cart;

import com.vichika.ecommercesystem.cart.dto.request.AddToCartRequest;
import com.vichika.ecommercesystem.cart.dto.response.CartResponse;
import com.vichika.ecommercesystem.cart.service.CartService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<APIResponse<CartResponse>> addToCart(@Valid @RequestBody AddToCartRequest request){
        return ResponseEntity.ok(APIResponse.create(cartService.addToCart(request)));
    }
}
