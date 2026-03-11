package com.example.recruitment.user.dto;

import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String role) {
}