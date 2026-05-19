package com.vichika.ecommercesystem.auth.service;

import com.vichika.ecommercesystem.auth.dto.request.AuthRequest;
import com.vichika.ecommercesystem.auth.dto.response.JwtResponse;
import com.vichika.ecommercesystem.auth.repository.UserRepository;
import com.vichika.ecommercesystem.security.jwt.JwtService;
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

    public JwtResponse loginAuth(AuthRequest request) {
        authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.email(),
                       request.password()
               )
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var accessToken = jwtService.generateAccessToken(user);
        return new JwtResponse(accessToken);
    }
}
