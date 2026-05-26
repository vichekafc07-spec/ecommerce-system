package com.vichika.ecommercesystem.checkout.mapper;

import com.vichika.ecommercesystem.checkout.dto.response.OrderItemResponse;
import com.vichika.ecommercesystem.checkout.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "product.id" , target = "productId")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
