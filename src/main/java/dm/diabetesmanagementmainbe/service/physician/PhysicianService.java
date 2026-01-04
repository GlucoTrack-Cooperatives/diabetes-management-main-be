package dm.diabetesmanagementmainbe.service.physician;


import dm.diabetesmanagementmainbe.controller.physician.dto.InvitePatientRequest;
import dm.diabetesmanagementmainbe.controller.physician.dto.PatientOverviewDTO;
import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import dm.diabetesmanagementmainbe.dao.repository.tracker.GlucoseReadingRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PhysicianRepository;
import dm.diabetesmanagementmainbe.dtos.PatientDTO;
import dm.diabetesmanagementmainbe.service.exception.DiabetesManagementException;
import dm.diabetesmanagementmainbe.service.exception.ExceptionErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhysicianService {

    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;
    private final GlucoseReadingRepository glucoseReadingRepository;

    @Transactional
    public void invitePatient(UUID physicianId, InvitePatientRequest request) {
        log.info("Physician {} inviting patient {}", physicianId, request.getEmail());

        Physician physician = physicianRepository.findById(physicianId)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        var patient = patientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Patient with email " + request.getEmail() + " not found"));

        if (patient.getPhysician() != null && patient.getIsPhysicianConfirmed()) {
            // Handle case where patient already has a confirmed physician if necessary
            // For now, we allow overriding or just throw logic
            log.warn("Patient already has a physician assigned.");
        }

        patient.setPhysician(physician);
        patient.setIsPhysicianConfirmed(false);
        patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public List<PatientOverviewDTO> getPatients(UUID physicianId) {
        Physician physician = physicianRepository.findById(physicianId)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        return physician.getPatients().stream()
                .map(patient -> {
                    // 1. Calculate Age from DOB
                    int age = 0;
                    if (patient.getDob() != null) {
                        age = Period.between(patient.getDob(), LocalDate.now()).getYears();
                    }

                    // 2. Fetch Latest Glucose Reading (Snapshot)
                    var latestGlucose = glucoseReadingRepository.findFirstByPatientIdOrderByTimestampDesc(patient.getId());

                    // 3. Build DTO
                    return PatientOverviewDTO.builder()
                            .id(patient.getId())
                            .fullName(patient.getFirstName() + " " + patient.getSurname())
                            .email(patient.getEmail())
                            .phoneNumber(patient.getPhoneNumbers())
                            .age(age)
                            .isPhysicianConfirmed(patient.getIsPhysicianConfirmed())
                            // Map optional glucose data safely
                            .latestGlucoseValue(latestGlucose.map(GlucoseReading::getValue).orElse(null))
                            .latestGlucoseTrend(latestGlucose.map(GlucoseReading::getTrend).orElse(null))
                            .latestGlucoseTimestamp(latestGlucose.map(GlucoseReading::getTimestamp).orElse(null))
                            .build();
                })
                .collect(Collectors.toList());
    }
}