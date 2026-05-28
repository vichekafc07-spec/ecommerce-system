package com.vichika.ecommercesystem.product.service;

import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.product.dto.request.WishlistRequest;
import com.vichika.ecommercesystem.product.dto.response.WishlistResponse;

public interface WishlistService {
    WishlistResponse createWishlist(WishlistRequest request);

    PageResponse<WishlistResponse> getMyWishlist(Long productId, String sortBy, String sortAs, Integer page, Integer size);

    void removeWishlist(Long productId);
}
