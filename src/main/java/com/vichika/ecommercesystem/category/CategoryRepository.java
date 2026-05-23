package com.vichika.ecommercesystem.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Byte> , JpaSpecificationExecutor<Category> {

    boolean existsByCode(String code);

    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT c FROM Category c WHERE c.id = :id")
    Optional<Category> findByIdIncludeDeleted(@Param("id") Byte id);

    Optional<Category> findByIdAndDeletedFalse(Byte id);
}
