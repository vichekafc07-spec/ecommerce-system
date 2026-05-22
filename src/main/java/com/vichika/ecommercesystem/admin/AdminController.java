package com.vichika.ecommercesystem.admin;

import com.vichika.ecommercesystem.admin.dto.request.PermissionRequest;
import com.vichika.ecommercesystem.admin.dto.request.RolePermissionRequest;
import com.vichika.ecommercesystem.admin.dto.request.RoleRequest;
import com.vichika.ecommercesystem.admin.dto.request.UserRoleRequest;
import com.vichika.ecommercesystem.admin.dto.response.PermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RolePermissionResponse;
import com.vichika.ecommercesystem.admin.dto.response.RoleResponse;
import com.vichika.ecommercesystem.admin.dto.response.UserRoleResponse;
import com.vichika.ecommercesystem.admin.service.AdminService;
import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // Role Feature
    @GetMapping("/roles")
    public ResponseEntity<APIResponse<PageResponse<RoleResponse>>> getAll(@RequestParam(required = false) String sortBy,
                                                                          @RequestParam(required = false) String sortAs,
                                                                          @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                          @RequestParam(required = false,defaultValue = "5") Integer size){
        return ResponseEntity.ok(APIResponse.ok(adminService.getAllRole(sortBy,sortAs,page,size)));
    }

    @PostMapping("/roles")
    public ResponseEntity<APIResponse<RoleResponse>> create(@Valid @RequestBody RoleRequest request){
        return ResponseEntity.ok(APIResponse.create(adminService.createRole(request)));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<APIResponse<RoleResponse>> update(@PathVariable Integer id,
                                                            @Valid @RequestBody RoleRequest request){
        return ResponseEntity.ok(APIResponse.ok(adminService.updateRole(id,request)));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        adminService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/roles/user/{userId}")
    public ResponseEntity<APIResponse<UserRoleResponse>> assignUserRole(@PathVariable Long userId,
                                                                        @Valid @RequestBody UserRoleRequest request){
        return ResponseEntity.ok(APIResponse.ok(adminService.assignUserRole(userId,request)));
    }

    // Permission Feature
    @GetMapping("/permissions")
    public ResponseEntity<APIResponse<PageResponse<PermissionResponse>>> getAllPermission(@RequestParam(required = false) String sortBy,
                                                                                          @RequestParam(required = false) String sortAs,
                                                                                          @RequestParam(required = false,defaultValue = "1") Integer page,
                                                                                          @RequestParam(required = false,defaultValue = "5") Integer size){
        return ResponseEntity.ok(APIResponse.ok(adminService.getAllPermissions(sortBy,sortAs,page,size)));
    }

    @PostMapping("/permissions")
    public ResponseEntity<APIResponse<PermissionResponse>> createPermission(@Valid @RequestBody PermissionRequest request){
        return ResponseEntity.ok(APIResponse.create(adminService.createPermissions(request)));
    }

    @PutMapping("/permissions/{id}")
    public ResponseEntity<APIResponse<PermissionResponse>> updatePermission(@PathVariable Integer id,
                                                                            @Valid @RequestBody PermissionRequest request){
        return ResponseEntity.ok(APIResponse.ok(adminService.updatePermissions(id,request)));
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Integer id){
        adminService.deletePermissions(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/permissions/role/{roleId}")
    public ResponseEntity<APIResponse<RolePermissionResponse>> assignRolePermission(@PathVariable Integer roleId,
                                                                                    @Valid @RequestBody RolePermissionRequest request){
        return ResponseEntity.ok(APIResponse.ok(adminService.assignRolePermission(roleId,request)));
    }

}
