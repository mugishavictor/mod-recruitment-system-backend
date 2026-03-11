package com.example.recruitment.integration;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NidSimulationService {

    public Map<String, Object> lookup(String nid) {
        return Map.of(
                "nid", nid,
                "firstName", "Victor",
                "lastName", "Mugisha",
                "dateOfBirth", "1998-06-15",
                "address", "Kigali");
    }
}