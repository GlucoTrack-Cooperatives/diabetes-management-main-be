package dm.diabetesmanagementmainbe.service.physician;

import dm.diabetesmanagementmainbe.controller.physician.dto.InvitePatientRequest;
import dm.diabetesmanagementmainbe.controller.physician.dto.PatientOverviewDTO;
import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import dm.diabetesmanagementmainbe.dao.repository.tracker.GlucoseReadingRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PhysicianRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhysicianService {

    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;
    private final GlucoseReadingRepository glucoseReadingRepository;

    /**
     * Invite a patient to connect with the physician.
     */
    @Transactional
    public void invitePatient(UUID physicianId, InvitePatientRequest request) {
        log.info("Physician {} inviting patient {}", physicianId, request.getEmail());

        Physician physician = physicianRepository.findById(physicianId)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        var patient = patientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Patient with email " + request.getEmail() + " not found"));

        // Logic to link patient to physician
        patient.setPhysician(physician);
        patient.setIsPhysicianConfirmed(false);
        patientRepository.save(patient);
    }

    /**
     * Get the overview list of patients for a specific physician.
     * OPTIMIZED: Uses a single query to fetch latest stats for all patients (eliminating N+1 problem).
     */
    @Transactional(readOnly = true)
    public List<PatientOverviewDTO> getPatients(UUID physicianId) {
        Physician physician = physicianRepository.findById(physicianId)
                .orElseThrow(() -> new RuntimeException("Physician not found"));

        var patients = physician.getPatients();

        if (patients.isEmpty()) {
            return Collections.emptyList();
        }

        // 1. Collect all Patient IDs
        List<UUID> patientIds = patients.stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        // 2. Bulk fetch latest readings in ONE query
        Map<UUID, GlucoseReading> readingsMap = glucoseReadingRepository.findLatestReadingsForPatients(patientIds)
                .stream()
                .collect(Collectors.toMap(
                        reading -> reading.getPatient().getId(),
                        Function.identity()
                ));

        // 3. Map to DTOs using the in-memory map
        return patients.stream()
                .map(patient -> {
                    // Calculate Age
                    int age = 0;
                    if (patient.getDob() != null) {
                        age = Period.between(patient.getDob(), LocalDate.now()).getYears();
                    }

                    // Get reading from map (O(1) lookup)
                    GlucoseReading latestGlucose = readingsMap.get(patient.getId());

                    return PatientOverviewDTO.builder()
                            .id(patient.getId())
                            .fullName(patient.getFirstName() + " " + patient.getSurname())
                            .email(patient.getEmail())
                            .phoneNumber(patient.getPhoneNumbers())
                            .age(age)
                            .isPhysicianConfirmed(patient.getIsPhysicianConfirmed())
                            .latestGlucoseValue(latestGlucose != null ? latestGlucose.getValue() : null)
                            .latestGlucoseTrend(latestGlucose != null ? latestGlucose.getTrend() : null)
                            .latestGlucoseTimestamp(latestGlucose != null ? latestGlucose.getTimestamp() : null)
                            .build();
                })
                .collect(Collectors.toList());
    }
}