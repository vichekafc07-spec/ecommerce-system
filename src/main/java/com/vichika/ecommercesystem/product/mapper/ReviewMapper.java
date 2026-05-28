package com.vichika.ecommercesystem.product.mapper;

import com.vichika.ecommercesystem.product.dto.request.ReviewRequest;
import com.vichika.ecommercesystem.product.dto.response.ReviewResponse;
import com.vichika.ecommercesystem.product.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "productId" , target = "product.id")
    Review toEntity(ReviewRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "productName", source = "product.name")
    ReviewResponse toResponse(Review review);
}
