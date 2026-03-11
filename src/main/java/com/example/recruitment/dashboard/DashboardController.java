package com.example.recruitment.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('HR','SUPER_ADMIN')")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return dashboardService.stats();
    }
}