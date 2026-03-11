package com.example.recruitment.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationReviewRepository extends JpaRepository<ApplicationReview, Long> {
    Optional<ApplicationReview> findTopByApplicationIdOrderByReviewedAtDesc(Long applicationId);
}