package com.example.recruitment.application.dto;

public record StatusLookupResponse(
        String referenceCode,
        String applicantName,
        String status,
        String lastUpdated,
        String reviewReason) {
}