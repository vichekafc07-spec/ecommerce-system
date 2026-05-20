package com.vichika.ecommercesystem.admin.service;

import com.vichika.ecommercesystem.admin.AdminMapper;
import com.vichika.ecommercesystem.admin.dto.request.PermissionRequest;
import com.vichika.ecommercesystem.admin.dto.request.RolePermissionRequest;
import com.vichika.ecommercesystem.admin.dto.request.RoleRequest;
import com.vichika.ecommercesystem.admin.dto.request.UserRoleRequest;
import com.vichika.ecommercesystem.admin.dto.response.PermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RolePermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RoleResponse;
import com.vichika.ecommercesystem.admin.dto.response.UserRoleResponse;
import com.vichika.ecommercesystem.auth.model.AppUser;
import com.vichika.ecommercesystem.auth.model.Permission;
import com.vichika.ecommercesystem.auth.model.Role;
import com.vichika.ecommercesystem.auth.repository.PermissionRepository;
import com.vichika.ecommercesystem.auth.repository.RoleRepository;
import com.vichika.ecommercesystem.auth.repository.UserRepository;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.common.SortResponse;
import com.vichika.ecommercesystem.exceptions.BadRequestException;
import com.vichika.ecommercesystem.exceptions.DuplicateResourceException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final RoleRepository roleRepository;
    private final AdminMapper adminMapper;
    private final PermissionRepository permissionRepository;

    public static final String ROLE_PREFIX = "ROLE_";
    private final UserRepository userRepository;

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
    public void deleteRole(Integer id) {
        var r = getRoleById(id);
        roleRepository.delete(r);
    }

    @Override
    public UserRoleResponse assignUserRole(Long userId, UserRoleRequest request) {
        var user = getUserById(userId);
        var role = new HashSet<>(roleRepository.findAllById(request.roleIds()));

        if (role.isEmpty()) {
            throw new BadRequestException("No valid roles found");
        }
        user.setRoles(role);
        userRepository.save(user);

        Set<String> roleName = role.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return new UserRoleResponse(userId , roleName);
    }

    @Override
    public PageResponse<PermissionResponse> getAllPermissions(String sortBy, String sortAs, Integer page, Integer size) {
        List<String> allowSort = List.of("id");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page - 1, size ,sort);
        Page<Permission> permissionPage = permissionRepository.findAll(pageable);
        return PageResponse.from(permissionPage, adminMapper::permissionResponse);
    }

    @Override
    public PermissionResponse createPermissions(PermissionRequest request) {
        if (permissionRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Permission with name " + request.name() + " already exists");
        }
        var p = new Permission();
        p.setName(request.name());
        return adminMapper.permissionResponse(permissionRepository.save(p));
    }

    @Override
    public PermissionResponse updatePermissions(Integer id, PermissionRequest request) {
        var p = getPermissionById(id);
        p.setName(request.name());
        return adminMapper.permissionResponse(permissionRepository.save(p));
    }

    @Override
    public void deletePermissions(Integer id) {
        var p = getPermissionById(id);
        permissionRepository.delete(p);
    }

    @Override
    public RolePermissionResponse assignRolePermission(Integer roleId, RolePermissionRequest request) {
        var role = getRoleById(roleId);
        var permission = new HashSet<>(permissionRepository.findAllById(request.permissionIds()));
        if (permission.isEmpty()){
            throw new BadRequestException("No valid permissions found");
        }
        role.setPermissions(permission);
        roleRepository.save(role);

        Set<String> permissionName = permission.stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        return new RolePermissionResponse(roleId,permissionName);
    }

    private Role getRoleById(Integer id){
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
    }

    private Permission getPermissionById(Integer id){
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + id));
    }

    private AppUser getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
}
