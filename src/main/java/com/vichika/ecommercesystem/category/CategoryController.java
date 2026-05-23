package com.vichika.ecommercesystem.category;

import com.vichika.ecommercesystem.category.service.CategoryService;
import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<APIResponse<PageResponse<CategoryResponse>>> getAll(@RequestParam(required = false) Byte id,
                                                                              @RequestParam(required = false) String name,
                                                                              @RequestParam(required = false) String code,
                                                                              @RequestParam(required = false) String sortBy,
                                                                              @RequestParam(required = false) String sortAs,
                                                                              @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                              @RequestParam(required = false,defaultValue = "5") Integer size){
        return ResponseEntity.ok(APIResponse.ok(categoryService.getAllCategory(id,name,code,sortBy,sortAs,page,size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryResponse>> getCategoryById(@PathVariable Byte id){
        return ResponseEntity.ok(APIResponse.ok(categoryService.getCategoryById(id)));
    }

    @PostMapping
    public ResponseEntity<APIResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(APIResponse.create(categoryService.createCategory(categoryRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryResponse>> update(@PathVariable Byte id,
                                                                @Valid @RequestBody CategoryRequest request){
        return ResponseEntity.ok(APIResponse.ok(categoryService.updateCategory(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Byte id){
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<APIResponse<CategoryResponse>> restore(@PathVariable Byte id){
        return ResponseEntity.ok(APIResponse.ok(categoryService.restoreCategory(id)));
    }

}
