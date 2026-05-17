package com.vichika.ecommercesystem.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByCode(String code);
}
