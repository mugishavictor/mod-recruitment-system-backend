package com.example.recruitment.application;

import com.example.recruitment.application.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hr/applications")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('HR','SUPER_ADMIN')")
public class HrApplicationController {

    private final HrApplicationService hrApplicationService;

    @GetMapping
    public List<ApplicationResponse> getLatest10() {
        return hrApplicationService.getLatest10SortedAlphabetically();
    }

    @GetMapping("/{id}")
    public HrApplicationDetailsResponse getOne(@PathVariable Long id) {
        return hrApplicationService.getById(id);
    }

    @PatchMapping("/{id}/review")
    public void review(@PathVariable Long id, @Valid @RequestBody ReviewApplicationRequest request) {
        hrApplicationService.review(id, request);
    }
}