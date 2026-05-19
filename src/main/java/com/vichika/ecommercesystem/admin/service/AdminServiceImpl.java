package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.admin.AdminMapper;
import com.vichika.ecommercesystem.admin.dto.RoleRequest;
import com.vichika.ecommercesystem.admin.dto.RoleResponse;
import com.vichika.ecommercesystem.auth.model.Role;
import com.vichika.ecommercesystem.auth.repository.RoleRepository;
import com.vichika.ecommercesystem.exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final RoleRepository roleRepository;
    private final AdminMapper adminMapper;
    public static final String ROLE_PREFIX = "ROLE_";

    @Override
    public RoleResponse createRole(RoleRequest request) {

        String roleName = request.name().trim().toUpperCase();
        String prefixedName = ROLE_PREFIX + roleName;

        if (roleRepository.existsByNameIgnoreCase(prefixedName)) {
            throw new DuplicateResourceException("Role with name " + roleName + " already exists");
        }

        var r = new Role();
        r.setName(prefixedName);

        return adminMapper.roleResponse(roleRepository.save(r));
    }
}
