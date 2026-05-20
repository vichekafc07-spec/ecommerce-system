package com.vichika.ecommercesystem.auth.repository;

import com.vichika.ecommercesystem.auth.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    boolean existsByNameIgnoreCase(String name);
}
