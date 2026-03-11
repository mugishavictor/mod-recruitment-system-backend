package com.example.recruitment.application;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CvFileRepository extends JpaRepository<CvFile, Long> {
}