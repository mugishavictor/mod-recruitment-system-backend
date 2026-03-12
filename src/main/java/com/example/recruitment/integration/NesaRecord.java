package com.example.recruitment.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NesaRecord {
    private String nid;
    private String schoolName;
    private String candidateNumber;
    private String academicYear;
    private String grade;
    private String combination;
    private String optionAttended;
}