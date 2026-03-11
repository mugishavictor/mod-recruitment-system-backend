package com.example.recruitment.auth;

public record LoginResponse(
        String token,
        String email,
        String fullName,
        String role) {
}