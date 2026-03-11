package com.example.recruitment.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SubmitApplicationRequest(
        @NotBlank String nid,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String phone,
        @Email @NotBlank String email,
        String dateOfBirth,
        String address,
        String grade,
        String schoolOption) {
}