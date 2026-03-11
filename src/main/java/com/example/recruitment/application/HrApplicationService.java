package com.example.recruitment.application;

import com.example.recruitment.application.dto.*;
import com.example.recruitment.common.enums.ApplicationStatus;
import com.example.recruitment.user.User;
import com.example.recruitment.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HrApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ApplicationReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getLatest10SortedAlphabetically() {
        return jobApplicationRepository.findTop10ByOrderBySubmittedAtDesc()
                .stream()
                .sorted(Comparator.comparing(a -> a.getApplicant().getFullName().toLowerCase()))
                .map(a -> new ApplicationResponse(
                        a.getId(),
                        a.getReferenceCode(),
                        a.getApplicant().getFullName(),
                        a.getStatus().name()))
                .toList();
    }

    @Transactional(readOnly = true)
    public HrApplicationDetailsResponse getById(Long id) {
        JobApplication application = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        Applicant applicant = application.getApplicant();

        return new HrApplicationDetailsResponse(
                application.getId(),
                application.getReferenceCode(),
                applicant.getFullName(),
                applicant.getNid(),
                applicant.getEmail(),
                applicant.getPhone(),
                applicant.getAddress(),
                applicant.getGrade(),
                applicant.getSchoolOption(),
                application.getStatus().name(),
                application.getCvFile() != null ? application.getCvFile().getFilePath() : null);
    }

    @Transactional
    public void review(Long applicationId, ReviewApplicationRequest request) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User reviewer = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Reviewer not found"));

        if ("APPROVED".equalsIgnoreCase(request.decision())) {
            application.setStatus(ApplicationStatus.APPROVED);
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
        }

        jobApplicationRepository.save(application);

        reviewRepository.save(
                ApplicationReview.builder()
                        .application(application)
                        .reviewedBy(reviewer)
                        .decision(request.decision().toUpperCase())
                        .reason(request.reason())
                        .build());
    }
}