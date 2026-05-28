package com.vichika.ecommercesystem.cart;

import com.vichika.ecommercesystem.cart.dto.request.AddToCartRequest;
import com.vichika.ecommercesystem.cart.dto.request.UpdateCartItemRequest;
import com.vichika.ecommercesystem.cart.dto.response.CartResponse;
import com.vichika.ecommercesystem.cart.service.CartService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<APIResponse<CartResponse>> addToCart(@Valid @RequestBody AddToCartRequest request){
        return ResponseEntity.ok(APIResponse.create(cartService.addToCart(request)));
    }

    @GetMapping("/user")
    public ResponseEntity<APIResponse<CartResponse>> getUserCart(){
        return ResponseEntity.ok(APIResponse.ok(cartService.getUserCart()));
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<APIResponse<CartResponse>> updateQuantity(@PathVariable Long itemId,
                                                                    @Valid @RequestBody UpdateCartItemRequest request){
        return ResponseEntity.ok(APIResponse.ok(cartService.updateQuantity(itemId,request)));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> removeItem(@PathVariable Long itemId){
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(){
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
