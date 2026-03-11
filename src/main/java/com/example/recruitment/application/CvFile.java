package com.example.recruitment.application;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cv_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CvFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "stored_name")
    private String storedName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @PrePersist
    void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
}