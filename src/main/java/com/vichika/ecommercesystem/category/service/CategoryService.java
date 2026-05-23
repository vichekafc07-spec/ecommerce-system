package com.vichika.ecommercesystem.category.service;

import com.vichika.ecommercesystem.category.CategoryRequest;
import com.vichika.ecommercesystem.category.CategoryResponse;
import com.vichika.ecommercesystem.common.PageResponse;

public interface CategoryService {
    PageResponse<CategoryResponse> getAllCategory(Byte id, String name, String code, String sortBy, String sortAs, Integer page, Integer size);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse getCategoryById(Byte id);

    CategoryResponse updateCategory(Byte id, CategoryRequest request);

    void deleteCategory(Byte id);

    CategoryResponse restoreCategory(Byte id);
}
