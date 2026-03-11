package com.example.recruitment.user;

import com.example.recruitment.common.enums.RoleName;
import com.example.recruitment.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse create(CreateUserRequest request) {
        Role role = roleRepository.findByName(RoleName.valueOf(request.role()))
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .isActive(true)
                .build();

        return toResponse(userRepository.save(user));
    }

    public UserResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Role role = roleRepository.findByName(RoleName.valueOf(request.role()))
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setRole(role);

        return toResponse(userRepository.save(user));
    }

    public UserResponse toggleStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setIsActive(!user.getIsActive());
        return toResponse(userRepository.save(user));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().getName().name(),
                user.getIsActive());
    }
}