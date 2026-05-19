package com.vichika.ecommercesystem.admin;

import com.vichika.ecommercesystem.admin.dto.RoleRequest;
import com.vichika.ecommercesystem.admin.dto.RoleResponse;
import com.vichika.ecommercesystem.admin.service.AdminService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/roles")
    public ResponseEntity<APIResponse<RoleResponse>> create(@Valid @RequestBody RoleRequest request){
        return ResponseEntity.ok(APIResponse.create(adminService.createRole(request)));
    }

}
