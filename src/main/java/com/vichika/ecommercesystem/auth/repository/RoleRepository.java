package com.vichika.ecommercesystem.auth.repository;

import com.vichika.ecommercesystem.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    boolean existsByNameIgnoreCase(String name);

    Optional<Role> findByName(String name);
}
