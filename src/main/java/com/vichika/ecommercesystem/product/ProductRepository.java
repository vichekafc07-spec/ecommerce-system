package com.vichika.ecommercesystem.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByCode(String code);

    @Query("select p from Product p join fetch p.category where p.id = :id")
    Optional<Product> findProductId(@Param("id") Long id);

    @Query("SELECT p FROM Product p where p.id = :id")
    Optional<Product> findByIdIncludeDeleted(@Param("id") Long id);

    Optional<Product> findByIdAndDeletedFalse(Long id);
}
