package com.vichika.ecommercesystem.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category,Byte> , JpaSpecificationExecutor<Category> {
}
