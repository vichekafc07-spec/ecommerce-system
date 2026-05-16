package com.vichika.ecommercesystem.category.service;

import com.vichika.ecommercesystem.category.Category;
import com.vichika.ecommercesystem.category.CategoryRepository;
import com.vichika.ecommercesystem.category.CategoryResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.common.SortResponse;
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
    public PageResponse<CategoryResponse> getAllCategory(Long id, String name, String code, String sortBy, String sortAs, Integer page, Integer size) {
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
}
