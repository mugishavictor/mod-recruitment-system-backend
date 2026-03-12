package com.example.recruitment.application;

import com.example.recruitment.application.dto.StatusLookupResponse;
import com.example.recruitment.application.dto.SubmissionResponse;
import com.example.recruitment.application.dto.SubmitApplicationRequest;
import com.example.recruitment.common.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicantRepository applicantRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final CvFileRepository cvFileRepository;
    private final ApplicationReviewRepository reviewRepository;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    @Transactional
    public SubmissionResponse submit(SubmitApplicationRequest request, MultipartFile cv) throws IOException {
        validateCv(cv);

        Applicant applicant = applicantRepository.findByNid(request.nid())
                .orElse(Applicant.builder().nid(request.nid()).build());

        applicant.setFirstName(request.firstName());
        applicant.setLastName(request.lastName());
        applicant.setPhone(request.phone());
        applicant.setEmail(request.email());
        applicant.setAddress(request.address());
        applicant.setGrade(request.grade());
        applicant.setSchoolOption(request.schoolOption());

        if (request.dateOfBirth() != null && !request.dateOfBirth().isBlank()) {
            applicant.setDateOfBirth(LocalDate.parse(request.dateOfBirth()));
        }

        applicant = applicantRepository.save(applicant);

        CvFile savedCv = storeCv(cv);

        JobApplication application = JobApplication.builder()
                .applicant(applicant)
                .referenceCode("APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status(ApplicationStatus.PENDING)
                .cvFile(savedCv)
                .build();

        application = jobApplicationRepository.save(application);

        return new SubmissionResponse(
                application.getId(),
                application.getReferenceCode(),
                applicant.getFullName(),
                application.getStatus().name());
    }

    @Transactional(readOnly = true)
    public StatusLookupResponse getStatus(String referenceCode) {
        JobApplication application = jobApplicationRepository.findByReferenceCode(referenceCode)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        String reason = reviewRepository.findTopByApplicationIdOrderByReviewedAtDesc(application.getId())
                .map(ApplicationReview::getReason)
                .orElse(null);

        return new StatusLookupResponse(
                application.getReferenceCode(),
                application.getApplicant().getFullName(),
                application.getStatus().name(),
                String.valueOf(application.getUpdatedAt()),
                reason);
    }

    private void validateCv(MultipartFile cv) {
        if (cv == null || cv.isEmpty()) {
            throw new IllegalArgumentException("CV file is required");
        }

        if (!ALLOWED_TYPES.contains(cv.getContentType())) {
            throw new IllegalArgumentException("Only PDF, DOC, and DOCX files are allowed");
        }

        if (cv.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("CV file size must not exceed 5MB");
        }
    }

    private CvFile storeCv(MultipartFile file) throws IOException {
        String storedName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path uploadDir = Paths.get("uploads");
        Files.createDirectories(uploadDir);

        Path target = uploadDir.resolve(storedName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return cvFileRepository.save(
                CvFile.builder()
                        .originalName(file.getOriginalFilename())
                        .storedName(storedName)
                        .contentType(file.getContentType())
                        .filePath(target.toString())
                        .fileSize(file.getSize())
                        .build());
    }
}