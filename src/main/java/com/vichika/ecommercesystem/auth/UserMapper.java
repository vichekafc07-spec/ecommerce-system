package com.vichika.ecommercesystem.auth;

import com.vichika.ecommercesystem.auth.dto.request.UserRequest;
import com.vichika.ecommercesystem.auth.dto.response.UserResponse;
import com.vichika.ecommercesystem.auth.model.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AppUser toEntity(UserRequest request);

    UserResponse toResponse(AppUser user);
}
