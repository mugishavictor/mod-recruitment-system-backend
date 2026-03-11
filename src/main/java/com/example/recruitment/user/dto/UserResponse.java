package com.example.recruitment.user.dto;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        String role,
        Boolean isActive) {
}