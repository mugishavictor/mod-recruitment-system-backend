package com.example.recruitment.application;

import com.example.recruitment.application.dto.*;
import com.example.recruitment.common.enums.ApplicationStatus;
import com.example.recruitment.user.User;
import com.example.recruitment.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
import java.nio.file.Paths;

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
                                .map(a -> {
                                        String reviewReason = reviewRepository
                                                        .findTopByApplicationIdOrderByReviewedAtDesc(a.getId())
                                                        .map(ApplicationReview::getReason)
                                                        .orElse(null);

                                        String cvFileName = a.getCvFile() != null ? a.getCvFile().getOriginalName()
                                                        : null;
                                        String cvDownloadUrl = a.getCvFile() != null
                                                        ? "/api/v1/hr/applications/" + a.getId() + "/cv"
                                                        : null;

                                        return new ApplicationResponse(
                                                        a.getId(),
                                                        a.getReferenceCode(),
                                                        a.getApplicant().getFullName(),
                                                        a.getStatus().name(),
                                                        reviewReason,
                                                        cvFileName,
                                                        cvDownloadUrl);
                                })
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

        @Transactional(readOnly = true)
        public ResponseEntity<Resource> downloadCv(Long applicationId) throws Exception {
                JobApplication application = jobApplicationRepository.findById(applicationId)
                                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

                CvFile cvFile = application.getCvFile();
                if (cvFile == null) {
                        throw new IllegalArgumentException("CV file not found");
                }

                Path path = Paths.get(cvFile.getFilePath());
                Resource resource = new UrlResource(path.toUri());

                if (!resource.exists() || !resource.isReadable()) {
                        throw new IllegalArgumentException("CV file cannot be read");
                }

                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION,
                                                "attachment; filename=\"" + cvFile.getOriginalName() + "\"")
                                .contentType(MediaType.parseMediaType(
                                                cvFile.getContentType() != null ? cvFile.getContentType()
                                                                : "application/octet-stream"))
                                .body(resource);
        }
}