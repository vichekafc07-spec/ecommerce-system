package com.vichika.ecommercesystem.admin;

import com.vichika.ecommercesystem.auth.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    RoleResponse roleResponse(Role role);
}
