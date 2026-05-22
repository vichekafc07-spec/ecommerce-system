package com.vichika.ecommercesystem.auth.controller;

import com.vichika.ecommercesystem.auth.dto.request.AuthRequest;
import com.vichika.ecommercesystem.auth.dto.response.AccessTokenResponse;
import com.vichika.ecommercesystem.auth.dto.response.AuthResponse;
import com.vichika.ecommercesystem.auth.dto.response.JwtResponse;
import com.vichika.ecommercesystem.auth.service.AuthService;
import com.vichika.ecommercesystem.common.APIResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse<JwtResponse>> login(@Valid @RequestBody AuthRequest request , HttpServletResponse response){
        return ResponseEntity.ok(APIResponse.ok(authService.loginAuth(request,response)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<AccessTokenResponse>> refresh(@CookieValue("refreshToken") String refreshToken){
        return ResponseEntity.ok(APIResponse.ok(authService.refreshToken(refreshToken)));
    }

    @GetMapping("/principle")
    public ResponseEntity<APIResponse<AuthResponse>> principle(){
        return ResponseEntity.ok(APIResponse.ok(authService.getPrinciples()));
    }

}
