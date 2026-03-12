package com.example.recruitment.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simulations")
@RequiredArgsConstructor
public class SimulationController {

    private final NidSimulationService nidSimulationService;
    private final NesaSimulationService nesaSimulationService;

    @GetMapping("/nid/{nid}")
    public NidRecord nid(@PathVariable String nid) {
        return nidSimulationService.lookup(nid);
    }

    @GetMapping("/nesa/candidate/{candidateNumber}")
    public NesaRecord nesaByCandidate(@PathVariable String candidateNumber) {
        return nesaSimulationService.lookupByCandidateNumber(candidateNumber);
    }
}