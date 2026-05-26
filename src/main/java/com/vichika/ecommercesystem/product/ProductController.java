package com.vichika.ecommercesystem.product;

import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.product.dto.ProductRequest;
import com.vichika.ecommercesystem.product.dto.ProductResponse;
import com.vichika.ecommercesystem.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<ProductResponse>>> getAll(@RequestParam(required = false) Byte id,
                                                                            @RequestParam(required = false) String name,
                                                                            @RequestParam(required = false) String code,
                                                                            @RequestParam(required = false) Integer categoryId,
                                                                            @RequestParam(required = false) String sortBy,
                                                                            @RequestParam(required = false) String sortAs,
                                                                            @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                            @RequestParam(required = false,defaultValue = "5") Integer size){
        return ResponseEntity.ok(APIResponse.ok(productService.getAllProduct(id,name,code,categoryId,sortBy,sortAs,page,size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> getById(@PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<APIResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(APIResponse.create(productService.createProduct(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> update(@PathVariable Long id,
                                                               @Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(APIResponse.ok(productService.updateProduct(id,request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> restore(@PathVariable Long id){
        return ResponseEntity.ok(APIResponse.ok(productService.restoreProduct(id)));
    }

    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<ProductResponse>> uploadImage(@PathVariable Long id, MultipartFile file){
        return ResponseEntity.ok(APIResponse.ok(productService.uploadImages(id,file)));
    }

}
