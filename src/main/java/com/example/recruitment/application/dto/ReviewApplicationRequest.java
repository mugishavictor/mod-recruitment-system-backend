package com.example.recruitment.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ReviewApplicationRequest(
        @NotBlank String decision,
        @NotBlank String reason) {
}