package com.example.recruitment.application;

import com.example.recruitment.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "application_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "application_id")
    private JobApplication application;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @Column(nullable = false)
    private String decision;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @PrePersist
    void onCreate() {
        reviewedAt = LocalDateTime.now();
    }
}