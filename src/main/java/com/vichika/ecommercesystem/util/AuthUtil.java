package com.vichika.ecommercesystem.util;

import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.auth.repository.UserRepository;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public AppUser getCurrentUser(){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByNameWithRoles(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
