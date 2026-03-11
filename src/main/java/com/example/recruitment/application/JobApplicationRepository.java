package com.example.recruitment.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Optional<JobApplication> findByReferenceCode(String referenceCode);

    List<JobApplication> findTop10ByOrderBySubmittedAtDesc();
}