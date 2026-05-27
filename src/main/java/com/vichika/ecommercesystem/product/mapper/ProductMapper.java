package com.vichika.ecommercesystem.product.mapper;

import com.vichika.ecommercesystem.product.dto.request.ProductRequest;
import com.vichika.ecommercesystem.product.dto.response.ProductResponse;
import com.vichika.ecommercesystem.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductRequest request);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);

    void toUpdate(ProductRequest request, @MappingTarget Product product);
}
