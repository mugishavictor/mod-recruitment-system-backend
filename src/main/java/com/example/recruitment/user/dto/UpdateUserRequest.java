package com.example.recruitment.user.dto;

import jakarta.validation.constraints.*;

public record UpdateUserRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String role) {
}