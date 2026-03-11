package com.example.recruitment.user;

import com.example.recruitment.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return userService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public UserResponse toggleStatus(@PathVariable Long id) {
        return userService.toggleStatus(id);
    }
}