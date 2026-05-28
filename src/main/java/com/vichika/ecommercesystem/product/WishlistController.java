package com.vichika.ecommercesystem.product;

import com.vichika.ecommercesystem.cart.dto.response.CartResponse;
import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.product.dto.request.WishlistRequest;
import com.vichika.ecommercesystem.product.dto.response.WishlistResponse;
import com.vichika.ecommercesystem.product.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<APIResponse<WishlistResponse>> create(@Valid @RequestBody WishlistRequest request) {
        return ResponseEntity.ok(APIResponse.create(wishlistService.createWishlist(request)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<WishlistResponse>>> getMyWishlist(@RequestParam(required = false) Long productId,
                                                                                     @RequestParam(required = false) String sortBy,
                                                                                     @RequestParam(required = false) String sortAs,
                                                                                     @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                                     @RequestParam(required = false,defaultValue = "5") Integer size){
        return ResponseEntity.ok(APIResponse.ok(wishlistService.getMyWishlist(productId,sortBy,sortAs,page,size)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> remove(@PathVariable Long productId) {
        wishlistService.removeWishlist(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<APIResponse<Long>> count() {
        return ResponseEntity.ok(APIResponse.ok(wishlistService.count()));
    }

    @PostMapping("/move-to-cart")
    public ResponseEntity<APIResponse<CartResponse>> moveToCart(@RequestBody WishlistRequest request) {
        return ResponseEntity.ok(APIResponse.ok(wishlistService.moveToCart(request)));
    }
}
