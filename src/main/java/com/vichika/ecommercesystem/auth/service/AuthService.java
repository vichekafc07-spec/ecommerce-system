package com.vichika.ecommercesystem.auth.service;

import com.vichika.ecommercesystem.auth.dto.request.AuthRequest;
import com.vichika.ecommercesystem.auth.dto.response.JwtResponse;
import com.vichika.ecommercesystem.auth.repository.UserRepository;
import com.vichika.ecommercesystem.security.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponse loginAuth(AuthRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.email(),
                       request.password()
               )
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/api/v1/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(604800);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponse(accessToken);
    }
}
