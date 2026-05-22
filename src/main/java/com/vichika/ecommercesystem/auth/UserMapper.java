package com.vichika.ecommercesystem.auth;

import com.vichika.ecommercesystem.auth.dto.request.UserRequest;
import com.vichika.ecommercesystem.auth.dto.response.AuthResponse;
import com.vichika.ecommercesystem.auth.dto.response.UserResponse;
import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.auth.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AppUser toEntity(UserRequest request);

    UserResponse toResponse(AppUser user);

    @Mapping(source = "roles" , target = "roles")
    AuthResponse authResponse(AppUser user);

    default String map(Role role){
        return role.getName();
    }
    default Set<String> map(Set<Role> roles) {
        return roles.stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

}
