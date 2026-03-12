package com.example.recruitment.integration;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NidSimulationService {

    private final List<NidRecord> nidRecords = List.of(
            NidRecord.builder()
                    .nid("1199887766554433")
                    .firstName("Victor")
                    .lastName("Mugisha")
                    .dateOfBirth("1998-06-15")
                    .gender("Male")
                    .address("Kigali")
                    .district("Gasabo")
                    .province("Kigali City")
                    .build(),

            NidRecord.builder()
                    .nid("1200123456789012")
                    .firstName("Aline")
                    .lastName("Uwase")
                    .dateOfBirth("2000-03-21")
                    .gender("Female")
                    .address("Musanze")
                    .district("Musanze")
                    .province("Northern")
                    .build(),

            NidRecord.builder()
                    .nid("1201456789012345")
                    .firstName("Jean")
                    .lastName("Habimana")
                    .dateOfBirth("2001-08-10")
                    .gender("Male")
                    .address("Huye")
                    .district("Huye")
                    .province("Southern")
                    .build(),

            NidRecord.builder()
                    .nid("1201678901234567")
                    .firstName("Claudine")
                    .lastName("Mukamana")
                    .dateOfBirth("1999-12-05")
                    .gender("Female")
                    .address("Rubavu")
                    .district("Rubavu")
                    .province("Western")
                    .build(),

            NidRecord.builder()
                    .nid("1201987654321098")
                    .firstName("Eric")
                    .lastName("Ndayisenga")
                    .dateOfBirth("1997-01-17")
                    .gender("Male")
                    .address("Kayonza")
                    .district("Kayonza")
                    .province("Eastern")
                    .build());

    public NidRecord lookup(String nid) {
        validateNid(nid);

        return nidRecords.stream()
                .filter(record -> record.getNid().equals(nid))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("NID record not found"));
    }

    private void validateNid(String nid) {
        if (nid == null || !nid.matches("^\\d{16}$")) {
            throw new IllegalArgumentException("NID must be exactly 16 digits");
        }
    }
}