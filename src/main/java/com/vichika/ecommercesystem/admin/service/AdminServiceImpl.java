package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.admin.AdminMapper;
import com.vichika.ecommercesystem.admin.dto.request.RoleRequest;
import com.vichika.ecommercesystem.admin.dto.response.PermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RoleResponse;
import com.vichika.ecommercesystem.auth.model.Role;
import com.vichika.ecommercesystem.auth.repository.RoleRepository;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.common.SortResponse;
import com.vichika.ecommercesystem.exceptions.DuplicateResourceException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageResponse<RoleResponse> getAllRole(String sortBy, String sortAs, Integer page, Integer size) {
        List<String> allowSort = List.of("id");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page - 1, size ,sort);
        Page<Role> rolePage = roleRepository.findAll(pageable);
        return PageResponse.from(rolePage, adminMapper::roleResponse);
    }

    @Override
    public RoleResponse updateRole(Integer id, RoleRequest request) {
        String roleName = request.name().trim().toUpperCase();
        String prefixedName = ROLE_PREFIX + roleName;

        var r = getRoleById(id);
        r.setName(prefixedName);

        return adminMapper.roleResponse(roleRepository.save(r));
    }

    @Override
    public void deleteProduct(Integer id) {
        var r = getRoleById(id);
        roleRepository.delete(r);
    }

    @Override
    public PermissionResponse getAllPermissions(String sortBy, String sortAs, Integer page, Integer size) {

        return null;
    }

    private Role getRoleById(Integer id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
    }
}
