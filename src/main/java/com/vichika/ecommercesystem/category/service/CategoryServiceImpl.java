package com.vichika.ecommercesystem.category.service;

import com.vichika.ecommercesystem.category.Category;
import com.vichika.ecommercesystem.category.CategoryRepository;
import com.vichika.ecommercesystem.category.CategoryRequest;
import com.vichika.ecommercesystem.category.CategoryResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.common.SortResponse;
import com.vichika.ecommercesystem.exceptions.DuplicateResourceException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.spec.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public PageResponse<CategoryResponse> getAllCategory(Byte id, String name, String code, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Category> spec = new SpecificationBuilder<Category>()
                .equal("id",id)
                .like("name",name)
                .like("code",code)
                .build();
        List<String> allowSort = List.of("id","name","code");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1, size,sort);
        Page<Category> categoryPage = categoryRepository.findAll(spec,pageable);

        return PageResponse.from(categoryPage, c -> new CategoryResponse(c.getId(),c.getName(),c.getCode()));
    }

    @Override
    public CategoryResponse getCategoryById(Byte id) {
        var category = getById(id);
        return toResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        if (categoryRepository.existsByNameIgnoreCase((request.name()))){
            throw new DuplicateResourceException("Category with name " + request.name() + " already exists");
        }

        if (categoryRepository.existsByCode(request.code())){
            throw new DuplicateResourceException("Category with code " + request.code() + " already exists");
        }
        var category = Category.builder()
                .name(request.name())
                .code(request.code())
                .build();

        return toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Byte id, CategoryRequest request) {
        var c = getById(id);
        c.setName(request.name());
        c.setCode(request.code());
        return toResponse(categoryRepository.save(c));
    }

    @Override
    public void deleteCategory(Byte id) {
        var c = getById(id);
        categoryRepository.delete(c);
    }

    private CategoryResponse toResponse(Category c){
        return new CategoryResponse(c.getId(), c.getName(), c.getCode());
    }

    private Category getById(Byte id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

}
