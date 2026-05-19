package com.vichika.ecommercesystem.security;

import com.vichika.ecommercesystem.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(p  -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toSet());
        return new User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
