package com.vichika.ecommercesystem.auth.repository;

import com.vichika.ecommercesystem.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    boolean existsByNameIgnoreCase(String name);
}
