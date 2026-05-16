package com.vichika.ecommercesystem.category;

import com.vichika.ecommercesystem.category.service.CategoryService;
import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<CategoryResponse>>> getAll(@RequestParam(required = false) Long id,
                                                                             @RequestParam(required = false) String name,
                                                                             @RequestParam(required = false) String code,
                                                                             @RequestParam(required = false) String sortBy,
                                                                             @RequestParam(required = false) String sortAs,
                                                                             @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                             @RequestParam(required = false,defaultValue = "1") Integer size){
        return ResponseEntity.ok(APIResponse.ok(categoryService.getAllCategory(id,name,code,sortBy,sortAs,page,size)));
    }

}
