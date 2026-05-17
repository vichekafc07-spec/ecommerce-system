package com.vichika.ecommercesystem.product.service;

import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.product.dto.ProductResponse;

public interface ProductService {
    PageResponse<ProductResponse> getAllProduct(Byte id, String name, String code, Integer categoryId, String sortBy, String sortAs, Integer page, Integer size);
}
