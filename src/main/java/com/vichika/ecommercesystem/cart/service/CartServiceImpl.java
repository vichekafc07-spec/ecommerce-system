package com.vichika.ecommercesystem.cart.service;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.cart.dto.request.AddToCartRequest;
import com.vichika.ecommercesystem.cart.dto.request.UpdateCartItemRequest;
import com.vichika.ecommercesystem.cart.dto.response.CartResponse;
import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.cart.repository.CartItemRepository;
import com.vichika.ecommercesystem.cart.repository.CartRepository;
import com.vichika.ecommercesystem.exceptions.BadRequestException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.product.model.Product;
import com.vichika.ecommercesystem.product.repository.ProductRepository;
import com.vichika.ecommercesystem.util.AuthUtil;
import com.vichika.ecommercesystem.util.CartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final AuthUtil authUtil;
    private final CartUtil cartUtil;

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
                .orElseGet(() -> CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(0)
                        .build());

        int requestedQuantity = cartItem.getQuantity() + request.quantity();

        if (requestedQuantity > product.getQuantity()) {
            throw new BadRequestException("Available stock is only " + product.getQuantity());
        }

        cartItem.setQuantity(requestedQuantity);
        cartItemRepository.save(cartItem);

        return cartUtil.buildCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getUserCart() {

        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);

        return cartUtil.buildCartResponse(cart);
    }

    @Override
    public CartResponse updateQuantity(Long itemId, UpdateCartItemRequest request) {

        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);

        var cartItem = getCartItemById(itemId,cart);

        if (request.quantity() > cartItem.getProduct().getQuantity()) {
            throw new BadRequestException("Available stock is only " + cartItem.getProduct().getQuantity());
        }

        cartItem.setQuantity(request.quantity());
        cartItemRepository.save(cartItem);

        return cartUtil.buildCartResponse(cartItem.getCart());
    }

    @Override
    public void removeItem(Long itemId) {

        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);
        var cartItem = getCartItemById(itemId,cart);

        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart() {
        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);
        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
    }

    @Override
    public void deleteCart(Long cartId) {
        var user = authUtil.getCurrentUser();
        var cart = getCartUser(user);
        cartItemRepository.deleteByCartId(cartId);
        cartRepository.delete(cart);
    }

    private Product getProductById(Long id){
        return productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    private CartItem getCartItemById(Long id, Cart cart){
        return cartItemRepository.findByIdAndCart(id,cart)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id " + id));
    }

    private Cart getCartUser(AppUser user){
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

}
