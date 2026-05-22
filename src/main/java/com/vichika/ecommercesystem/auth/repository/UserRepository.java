package com.vichika.ecommercesystem.auth.repository;

import com.vichika.ecommercesystem.auth.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> , JpaSpecificationExecutor<AppUser> {
    boolean existsByEmail(String email);

    Optional<AppUser> findByEmail(String email);

    @Query("SELECT u FROM AppUser u join fetch u.roles where u.username = :username")
    Optional<AppUser> findByNameWithRoles(String username);
}
