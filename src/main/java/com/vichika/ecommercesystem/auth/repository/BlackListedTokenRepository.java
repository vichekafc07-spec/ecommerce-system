package com.vichika.ecommercesystem.auth.repository;

import com.vichika.ecommercesystem.auth.model.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken,Long> {
    boolean existsByToken(String token);
}
