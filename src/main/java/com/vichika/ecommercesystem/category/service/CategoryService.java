package com.vichika.ecommercesystem.category.service;

import com.vichika.ecommercesystem.category.CategoryResponse;
import com.vichika.ecommercesystem.common.PageResponse;

public interface CategoryService {
    PageResponse<CategoryResponse> getAllCategory(Long id, String name, String code, String sortBy, String sortAs, Integer page, Integer size);
}
