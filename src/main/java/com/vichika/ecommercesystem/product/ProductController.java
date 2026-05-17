package com.vichika.ecommercesystem.product;

import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.product.dto.ProductResponse;
import com.vichika.ecommercesystem.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
