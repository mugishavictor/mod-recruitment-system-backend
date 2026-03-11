package com.example.recruitment.application.dto;

public record HrApplicationDetailsResponse(
        Long id,
        String referenceCode,
        String applicantName,
        String nid,
        String email,
        String phone,
        String address,
        String grade,
        String schoolOption,
        String status,
        String cvFilePath) {
}