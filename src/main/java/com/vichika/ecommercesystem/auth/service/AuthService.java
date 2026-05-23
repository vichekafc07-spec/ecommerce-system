package com.vichika.ecommercesystem.auth.service;

import com.vichika.ecommercesystem.auth.UserMapper;
import com.vichika.ecommercesystem.auth.dto.request.AuthRequest;
import com.vichika.ecommercesystem.auth.dto.response.AccessTokenResponse;
import com.vichika.ecommercesystem.auth.dto.response.AuthResponse;
import com.vichika.ecommercesystem.auth.dto.response.JwtResponse;
import com.vichika.ecommercesystem.auth.repository.UserRepository;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.exceptions.TokenExpiredException;
import com.vichika.ecommercesystem.security.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public JwtResponse loginAuth(AuthRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.email(),
                       request.password()
               )
        );
        var user = userRepository.findByEmailAndDeletedFalse(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateAccessToken(user).generate();
        var refreshToken = jwtService.generateRefreshToken(user).generate();

        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/api/v1/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponse(accessToken,refreshToken);
    }

    public AccessTokenResponse refreshToken(String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired()){
            throw new TokenExpiredException("Refresh token expired or invalid");
        }

        String username = jwt.getUsername();

        var user = userRepository.findByNameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
        var accessToken = jwtService.generateAccessToken(user).generate();
        return new AccessTokenResponse(accessToken);
    }

    public AuthResponse getPrinciples() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = (String) authentication.getPrincipal();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.authResponse(user);
    }
}
