package dm.diabetesmanagementmainbe.controller.patient.dto.settings;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatePatientProfileRequest {
    private String firstName;
    private String surName;
    private String phoneNumber;
    private LocalDate dob;
    private LocalDate diagnosisDate;
    private String emergencyContactPhone;
}
