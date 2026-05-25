package com.vichika.ecommercesystem.checkout;

import com.vichika.ecommercesystem.checkout.dto.OrderItemResponse;
import com.vichika.ecommercesystem.checkout.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
