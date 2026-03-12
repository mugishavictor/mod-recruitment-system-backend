package com.example.recruitment.integration;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NesaSimulationService {

        private final List<NesaRecord> nesaRecords = List.of(
                        NesaRecord.builder()
                                        .nid("1199887766554433")
                                        .schoolName("FAWE Girls School")
                                        .candidateNumber("S6-24-001")
                                        .academicYear("2023-2024")
                                        .grade("A")
                                        .combination("MPC")
                                        .optionAttended("Mathematics-Physics-Computer Science")
                                        .build(),

                        NesaRecord.builder()
                                        .nid("1200123456789012")
                                        .schoolName("Lycée de Kigali")
                                        .candidateNumber("S6-24-002")
                                        .academicYear("2023-2024")
                                        .grade("B+")
                                        .combination("MEG")
                                        .optionAttended("Mathematics-Economics-Geography")
                                        .build(),

                        NesaRecord.builder()
                                        .nid("1201456789012345")
                                        .schoolName("GS Officiel de Butare")
                                        .candidateNumber("S6-24-003")
                                        .academicYear("2023-2024")
                                        .grade("A-")
                                        .combination("PCB")
                                        .optionAttended("Physics-Chemistry-Biology")
                                        .build(),

                        NesaRecord.builder()
                                        .nid("1201678901234567")
                                        .schoolName("Petit Séminaire Virgo Fidelis")
                                        .candidateNumber("S6-24-004")
                                        .academicYear("2023-2024")
                                        .grade("B")
                                        .combination("HEG")
                                        .optionAttended("History-Economics-Geography")
                                        .build(),

                        NesaRecord.builder()
                                        .nid("1201987654321098")
                                        .schoolName("Nyagatare Secondary School")
                                        .candidateNumber("S6-24-005")
                                        .academicYear("2023-2024")
                                        .grade("A")
                                        .combination("MCB")
                                        .optionAttended("Mathematics-Chemistry-Biology")
                                        .build());

        public NesaRecord lookupByCandidateNumber(String candidateNumber) {
                return nesaRecords.stream()
                                .filter(record -> record.getCandidateNumber().equalsIgnoreCase(candidateNumber))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("NESA record not found"));
        }

        private void validateNid(String nid) {
                if (nid == null || !nid.matches("^\\d{16}$")) {
                        throw new IllegalArgumentException("NID must be exactly 16 digits");
                }
        }
}