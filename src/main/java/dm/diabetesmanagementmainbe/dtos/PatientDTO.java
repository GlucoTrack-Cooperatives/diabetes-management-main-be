package dm.diabetesmanagementmainbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private UUID id;
    private String firstName;
    private String surName;
    private String phoneNumbers;
    private LocalDate dob;
    private LocalDate diagnosisDate;
    private String emergencyContactPhone;
    private LocalDateTime createdAt;
}
