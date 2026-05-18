package com.vichika.ecommercesystem.auth;

import com.vichika.ecommercesystem.auth.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public UserRequest createUser(UserRequest request) {

        return null;
    }
}
