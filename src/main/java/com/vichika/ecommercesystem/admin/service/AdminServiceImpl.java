package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleRequest request) {

    }
}
