package com.example.recruitment.application.dto;

public record SubmissionResponse(
        Long id,
        String referenceCode,
        String applicantName,
        String status) {
}