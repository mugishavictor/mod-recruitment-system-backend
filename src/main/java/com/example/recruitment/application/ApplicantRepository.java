package com.example.recruitment.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByNid(String nid);
}