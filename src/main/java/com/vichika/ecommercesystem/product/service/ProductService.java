package com.vichika.ecommercesystem.product.service;

import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.product.dto.request.ProductRequest;
import com.vichika.ecommercesystem.product.dto.response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    PageResponse<ProductResponse> getAllProduct(Byte id, String name, String code, Integer categoryId, String sortBy, String sortAs, Integer page, Integer size);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse restoreProduct(Long id);

    ProductResponse uploadImages(Long id, MultipartFile file);
}
