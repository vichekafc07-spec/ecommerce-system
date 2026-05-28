package com.vichika.ecommercesystem.product.service.impl;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.cart.dto.response.CartResponse;
import com.vichika.ecommercesystem.cart.model.Cart;
import com.vichika.ecommercesystem.cart.model.CartItem;
import com.vichika.ecommercesystem.cart.repository.CartItemRepository;
import com.vichika.ecommercesystem.cart.repository.CartRepository;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.common.SortResponse;
import com.vichika.ecommercesystem.exceptions.BadRequestException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.product.dto.request.WishlistRequest;
import com.vichika.ecommercesystem.product.dto.response.WishlistResponse;
import com.vichika.ecommercesystem.product.mapper.WishlistMapper;
import com.vichika.ecommercesystem.product.model.Product;
import com.vichika.ecommercesystem.product.model.Wishlist;
import com.vichika.ecommercesystem.product.repository.ProductRepository;
import com.vichika.ecommercesystem.product.repository.WishlistRepository;
import com.vichika.ecommercesystem.product.service.WishlistService;
import com.vichika.ecommercesystem.spec.SpecificationBuilder;
import com.vichika.ecommercesystem.util.AuthUtil;
import com.vichika.ecommercesystem.util.CartUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AuthUtil authUtil;
    private final CartUtil cartUtil;

    @Override
    public WishlistResponse createWishlist(WishlistRequest request) {

        var user = authUtil.getCurrentUser();
        var product = getProductById(request.productId());
        if (wishlistRepository.existsByUserAndProduct(user,product)){
            throw new BadRequestException("Wishlist already exists");
        }

        var wishlist = wishlistMapper.toEntity(request);
        wishlist.setProduct(product);
        wishlist.setUser(user);

        return wishlistMapper.toResponse(wishlistRepository.save(wishlist));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<WishlistResponse> getMyWishlist(Long productId, String sortBy, String sortAs, Integer page, Integer size) {

        var user = authUtil.getCurrentUser();

        Specification<Wishlist> spec = new SpecificationBuilder<Wishlist>()
                .equal("productId" , productId)
                .equal("user", user)
                .build();
        List<String> allowSort = List.of("productId");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1 , size,sort);
        Page<Wishlist> wishlistPage = wishlistRepository.findAll(spec,pageable);

        return PageResponse.from(wishlistPage,wishlistMapper::toResponse);
    }

    @Override
    public void removeWishlist(Long productId) {

        var user = authUtil.getCurrentUser();
        var product = getProductById(productId);
        var wishlist = getUserAndProduct(user,product);
        wishlistRepository.delete(wishlist);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        var user = authUtil.getCurrentUser();
        return wishlistRepository.countByUser(user);
    }

    @Override
    public CartResponse moveToCart(WishlistRequest request) {

        var user = authUtil.getCurrentUser();
        var product = getProductById(request.productId());

        var wishlist = getUserAndProduct(user,product);

        if (product.getQuantity() <= 0) {
            throw new BadRequestException("Product out of stock");
        }

        var cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    var newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });

        var cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    var item = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .quantity(0)
                            .build();
                    return cartItemRepository.save(item);
                });

        if (cartItem.getQuantity() + 1 > product.getQuantity()) {
            throw new BadRequestException("Insufficient stock");
        }

        cartItem.setQuantity(cartItem.getQuantity() + 1);

        cartItemRepository.save(cartItem);

        wishlistRepository.delete(wishlist);

        return cartUtil.buildCartResponse(cart);
    }

    private Product getProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }

    private Wishlist getUserAndProduct(AppUser user, Product product){
        return wishlistRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));
    }

}
