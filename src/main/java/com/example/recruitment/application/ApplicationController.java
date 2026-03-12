package com.example.recruitment.application;

import com.example.recruitment.application.dto.StatusLookupResponse;
import com.example.recruitment.application.dto.SubmissionResponse;
import com.example.recruitment.application.dto.SubmitApplicationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SubmissionResponse submit(
            @Valid @RequestPart("data") SubmitApplicationRequest request,
            @RequestPart("cv") MultipartFile cv) throws Exception {
        return applicationService.submit(request, cv);
    }

    @GetMapping("/status/{referenceCode}")
    public StatusLookupResponse getStatus(@PathVariable String referenceCode) {
        return applicationService.getStatus(referenceCode);
    }
}