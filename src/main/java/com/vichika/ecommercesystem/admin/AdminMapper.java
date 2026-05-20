package com.vichika.ecommercesystem.admin;

import com.vichika.ecommercesystem.admin.dto.response.PermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RoleResponse;
import com.vichika.ecommercesystem.auth.model.Permission;
import com.vichika.ecommercesystem.auth.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    RoleResponse roleResponse(Role role);
    PermissionResponse permissionResponse(Permission permission);
}
