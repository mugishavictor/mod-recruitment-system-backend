package com.example.recruitment.integration;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NesaSimulationService {

    public Map<String, Object> lookup(String nid) {
        return Map.of(
                "nid", nid,
                "grade", "A",
                "schoolOption", "Mathematics-Physics-Computer Science");
    }
}