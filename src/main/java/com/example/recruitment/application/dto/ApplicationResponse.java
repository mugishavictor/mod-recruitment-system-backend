package com.example.recruitment.application.dto;

public record ApplicationResponse(
                Long id,
                String referenceCode,
                String applicantName,
                String status,
                String reviewReason,
                String cvFileName,
                String cvDownloadUrl) {
}