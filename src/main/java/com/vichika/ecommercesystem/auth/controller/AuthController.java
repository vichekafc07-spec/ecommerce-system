package com.vichika.ecommercesystem.auth.controller;

import com.vichika.ecommercesystem.auth.dto.request.AuthRequest;
import com.vichika.ecommercesystem.auth.dto.response.JwtResponse;
import com.vichika.ecommercesystem.auth.service.AuthService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<JwtResponse>> login(@Valid @RequestBody AuthRequest request){
        return ResponseEntity.ok(APIResponse.ok(authService.loginAuth(request)));
    }

}
