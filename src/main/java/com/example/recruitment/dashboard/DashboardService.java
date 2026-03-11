package com.example.recruitment.dashboard;

import com.example.recruitment.application.JobApplicationRepository;
import com.example.recruitment.common.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final JobApplicationRepository jobApplicationRepository;

    public Map<String, Object> stats() {
        var all = jobApplicationRepository.findAll();

        long total = all.size();
        long approved = all.stream().filter(a -> a.getStatus() == ApplicationStatus.APPROVED).count();
        long rejected = all.stream().filter(a -> a.getStatus() == ApplicationStatus.REJECTED).count();
        long pending = all.stream().filter(
                a -> a.getStatus() == ApplicationStatus.SUBMITTED || a.getStatus() == ApplicationStatus.IN_REVIEW)
                .count();

        return Map.of(
                "totalApplications", total,
                "approvedApplications", approved,
                "rejectedApplications", rejected,
                "pendingApplications", pending);
    }
}