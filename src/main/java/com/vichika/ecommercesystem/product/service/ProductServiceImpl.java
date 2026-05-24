package com.vichika.ecommercesystem.product.service;

import com.vichika.ecommercesystem.category.Category;
import com.vichika.ecommercesystem.category.CategoryRepository;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.common.SortResponse;
import com.vichika.ecommercesystem.exceptions.DuplicateResourceException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.product.Product;
import com.vichika.ecommercesystem.product.ProductMapper;
import com.vichika.ecommercesystem.product.ProductRepository;
import com.vichika.ecommercesystem.product.dto.ProductRequest;
import com.vichika.ecommercesystem.product.dto.ProductResponse;
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
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public PageResponse<ProductResponse> getAllProduct(Byte id, String name, String code, Integer categoryId, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<Product> spec = new SpecificationBuilder<Product>()
                .equal("deleted",false)
                .equal("id", id)
                .like("name", name)
                .like("code", code)
                .equal("categoryId",categoryId)
                .build();
        List<String> allowSort = List.of("id","name","code","categoryId");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page -1 , size,sort);
        Page<Product> productPage = productRepository.findAll(spec,pageable);
        return PageResponse.from(productPage,productMapper::toResponse);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        var p = productRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return productMapper.toResponse(p);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByNameIgnoreCase(request.name())){
            throw new DuplicateResourceException("Product with name " + request.name() + " already exists");
        }
        if (productRepository.existsByCode(request.code())){
            throw new DuplicateResourceException(" Product with code " + request.code() + " already exists");
        }
        var c = getCategoryById(request.categoryId());
        var p = productMapper.toEntity(request);
        p.setCategory(c);
        p.setFinalPrice(p.totalPrice());

        return productMapper.toResponse(productRepository.save(p));
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        var p = getById(id);
        var c = getCategoryById(request.categoryId());
        productMapper.toUpdate(request,p);
        p.setCategory(c);
        p.setFinalPrice(p.totalPrice());

        return productMapper.toResponse(productRepository.save(p));
    }

    @Override
    public void deleteProduct(Long id) {
        var p = getById(id);
        productRepository.delete(p);
    }

    @Override
    public ProductResponse restoreProduct(Long id) {
        var p = productRepository.findByIdIncludeDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        p.setDeleted(false);
        p.setDeletedAt(null);

        return productMapper.toResponse(productRepository.save(p));
    }

    private Category getCategoryById(Byte id){
        return categoryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    private Product getById(Long id){
        return productRepository.findProductId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }
}
