package com.example.recruitment.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/simulations")
@RequiredArgsConstructor
public class SimulationController {

    private final NidSimulationService nidSimulationService;
    private final NesaSimulationService nesaSimulationService;

    @GetMapping("/nid/{nid}")
    public Map<String, Object> nid(@PathVariable String nid) {
        return nidSimulationService.lookup(nid);
    }

    @GetMapping("/nesa/{nid}")
    public Map<String, Object> nesa(@PathVariable String nid) {
        return nesaSimulationService.lookup(nid);
    }
}