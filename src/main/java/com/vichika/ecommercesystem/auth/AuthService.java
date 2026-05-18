package com.vichika.ecommercesystem.auth;

import com.vichika.ecommercesystem.auth.dto.request.UserRequest;
import com.vichika.ecommercesystem.auth.dto.request.UserUpdateRequest;
import com.vichika.ecommercesystem.auth.dto.response.UserResponse;
import com.vichika.ecommercesystem.common.PageResponse;
import com.vichika.ecommercesystem.common.SortResponse;
import com.vichika.ecommercesystem.exceptions.DuplicateResourceException;
import com.vichika.ecommercesystem.exceptions.ResourceNotFoundException;
import com.vichika.ecommercesystem.spec.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("User with email " + request.email() + " already exists");
        }
        var u = userMapper.toEntity(request);
        u.setPassword(encoder.encode(request.password()));
        return userMapper.toResponse(userRepository.save(u));
    }

    public PageResponse<UserResponse> getAllUser(Long id, String username, String email, String sortBy, String sortAs, Integer page, Integer size) {
        Specification<AppUser> spec = new SpecificationBuilder<AppUser>()
                .equal("id",id)
                .like("username",username)
                .like("email",email)
                .build();

        List<String> allowSort = List.of("id","username","email");
        var sort = SortResponse.sortResponse(sortBy,sortAs,allowSort);
        Pageable pageable = PageRequest.of(page - 1, size , sort);
        Page<AppUser> userPage = userRepository.findAll(spec,pageable);

        return PageResponse.from(userPage,userMapper::toResponse);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("User with email " + request.email() + " already exists");
        }
        var u = getUserById(id);
        u.setUsername(request.username());
        u.setEmail(request.email());

        return userMapper.toResponse(userRepository.save(u));
    }

    private AppUser getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
}
