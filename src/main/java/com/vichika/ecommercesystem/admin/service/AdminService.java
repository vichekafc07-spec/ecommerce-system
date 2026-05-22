package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.admin.dto.request.PermissionRequest;
import com.vichika.ecommercesystem.admin.dto.request.RolePermissionRequest;
import com.vichika.ecommercesystem.admin.dto.request.RoleRequest;
import com.vichika.ecommercesystem.admin.dto.request.UserRoleRequest;
import com.vichika.ecommercesystem.admin.dto.response.PermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RolePermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RoleResponse;
import com.vichika.ecommercesystem.admin.dto.response.UserRoleResponse;
import com.vichika.ecommercesystem.common.PageResponse;

public interface AdminService {
    RoleResponse createRole(RoleRequest request);

    PageResponse<RoleResponse> getAllRole(String sortBy, String sortAs, Integer page, Integer size);

    RoleResponse updateRole(Integer id, RoleRequest request);

    void deleteRole(Integer id);

    PageResponse<PermissionResponse> getAllPermissions(String sortBy, String sortAs, Integer page, Integer size);

    PermissionResponse createPermissions(PermissionRequest request);

    PermissionResponse updatePermissions(Integer id, PermissionRequest request);

    void deletePermissions(Integer id);

    UserRoleResponse assignUserRole(Long userId, UserRoleRequest request);

    RolePermissionResponse assignRolePermission(Integer roleId, RolePermissionRequest request);
}
