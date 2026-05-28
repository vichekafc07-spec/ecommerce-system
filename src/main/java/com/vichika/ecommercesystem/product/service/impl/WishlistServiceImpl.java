package com.vichika.ecommercesystem.product.service.impl;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;
    private final AuthUtil authUtil;

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
        var wishlist = wishlistRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist item not found"));

        wishlistRepository.delete(wishlist);
    }

    private Product getProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
    }

}
