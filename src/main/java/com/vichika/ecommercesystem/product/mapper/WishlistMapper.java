package com.vichika.ecommercesystem.product.mapper;

import com.vichika.ecommercesystem.product.dto.request.WishlistRequest;
import com.vichika.ecommercesystem.product.dto.response.WishlistResponse;
import com.vichika.ecommercesystem.product.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mapping(source = "productId" , target = "product.id")
    Wishlist toEntity(WishlistRequest request);

    @Mapping(target = "wishlistId", source = "id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "imageUrl", source = "product.imageUrl")
    WishlistResponse toResponse(Wishlist wishlist);
}
