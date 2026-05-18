package com.vichika.ecommercesystem.auth.repository;

import com.vichika.ecommercesystem.auth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<AppUser,Long> , JpaSpecificationExecutor<AppUser> {
    boolean existsByEmail(String email);
}
