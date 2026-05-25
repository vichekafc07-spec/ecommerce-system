package com.vichika.ecommercesystem.cart.service;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.cart.CartMapper;
import com.vichika.ecommercesystem.cart.dto.request.AddToCartRequest;
import com.vichika.ecommercesystem.cart.dto.request.UpdateCartItemRequest;
import com.vichika.ecommercesystem.cart.dto.response.CartItemResponse;
import com.vichika.ecommercesystem.cart.dto.response.CartResponse;
import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.cart.repository.CartItemRepository;
import com.vichika.ecommercesystem.cart.repository.CartRepository;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.product.Product;
import com.vichika.ecommercesystem.product.ProductRepository;
import com.vichika.ecommercesystem.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final AuthUtil authUtil;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponse addToCart(AddToCartRequest request) {

        var user = authUtil.getCurrentUser();
        var product = getProductById(request.productId());

        var cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    var newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });

        var cartItem = cartItemRepository.findByCartAndProduct(cart,product)
                .orElseGet(() -> {
                    var item = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(0)
                            .build();
                    return cartItemRepository.save(item);
                });
        cartItem.setQuantity(cartItem.getQuantity() + request.quantity());
        cartItemRepository.save(cartItem);

        return buildCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getUserCart() {

        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);

        return buildCartResponse(cart);
    }

    @Override
    public CartResponse updateQuantity(Long itemId, UpdateCartItemRequest request) {

        var cartItem = getCartItemById(itemId);
        cartItem.setQuantity(request.quantity());
        cartItemRepository.save(cartItem);

        return buildCartResponse(cartItem.getCart());
    }

    @Override
    public void removeItem(Long itemId) {

        var cartItem = getCartItemById(itemId);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart() {
        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);
        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
    }

    private CartResponse buildCartResponse(Cart cart){

        List<CartItem> items = cartItemRepository.findByCart(cart);

        List<CartItemResponse> itemResponses = items.stream()
                .map(cartMapper::toCartItemResponse)
                .toList();

        BigDecimal total = itemResponses.stream()
                .map(CartItemResponse::finalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponse(
                cart.getId(),
                itemResponses,
                total
        );
    }

    private Product getProductById(Long id){
        return productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    private CartItem getCartItemById(Long id){
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id " + id));
    }

    private Cart getCartUser(AppUser user){
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

}
