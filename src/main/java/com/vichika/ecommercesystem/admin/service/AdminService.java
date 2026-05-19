package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.admin.dto.RoleRequest;
import com.vichika.ecommercesystem.admin.dto.RoleResponse;

public interface AdminService {
    RoleResponse createRole(RoleRequest request);
}
