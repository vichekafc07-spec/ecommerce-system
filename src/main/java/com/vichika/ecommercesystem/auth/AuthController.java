package com.vichika.ecommercesystem.auth;

import com.vichika.ecommercesystem.auth.dto.request.UserRequest;
import com.vichika.ecommercesystem.auth.dto.request.UserUpdateRequest;
import com.vichika.ecommercesystem.auth.dto.response.UserResponse;
import com.vichika.ecommercesystem.common.APIResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<APIResponse<UserResponse>> create(@Valid @RequestBody UserRequest request){
        return ResponseEntity.ok(APIResponse.create(authService.createUser(request)));
    }

    @GetMapping("/users")
    public ResponseEntity<APIResponse<PageResponse<UserResponse>>> getAll(@RequestParam(required = false) Long id,
                                                                          @RequestParam(required = false) String username,
                                                                          @RequestParam(required = false) String email,
                                                                          @RequestParam(required = false) String sortBy,
                                                                          @RequestParam(required = false) String sortAs,
                                                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                          @RequestParam(required = false, defaultValue = "5") Integer size
                                                                          ){
        return ResponseEntity.ok(APIResponse.ok(authService.getAllUser(id,username,email,sortBy,sortAs,page,size)));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<APIResponse<UserResponse>> update(@PathVariable Long id,
                                                            @Valid @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(APIResponse.ok(authService.updateUser(id,request)));
    }

}
