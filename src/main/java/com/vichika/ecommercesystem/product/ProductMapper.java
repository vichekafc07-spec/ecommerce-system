package com.vichika.ecommercesystem.product;

import com.vichika.ecommercesystem.product.dto.ProductRequest;
import com.vichika.ecommercesystem.product.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category.id")
    Product toEntity(ProductRequest request);

    @Mapping(source = "category.name", target = "categoryName")
    ProductResponse toResponse(Product product);

    void toUpdate(Product product, @MappingTarget ProductRequest request);
}
