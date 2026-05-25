package com.vichika.ecommercesystem.checkout;

import com.vichika.ecommercesystem.checkout.dto.OrderItemResponse;
import com.vichika.ecommercesystem.checkout.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "product.id" , target = "productId")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
