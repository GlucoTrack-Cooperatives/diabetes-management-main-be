package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import dm.diabetesmanagementmainbe.dtos.DashboardDTO;
import dm.diabetesmanagementmainbe.dtos.KeyMetricsDTO;
import dm.diabetesmanagementmainbe.dao.model.logging.FoodLog;
import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import dm.diabetesmanagementmainbe.dao.model.logging.InsulinDose;
import dm.diabetesmanagementmainbe.dao.repository.logging.FoodLogRepository;
import dm.diabetesmanagementmainbe.dao.repository.tracker.GlucoseReadingRepository;
import dm.diabetesmanagementmainbe.dao.repository.logging.InsulinDoseRepository;
import dm.diabetesmanagementmainbe.dtos.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService implements IPatientService {

    private final GlucoseReadingRepository glucoseReadingRepository;
    private final FoodLogRepository foodLogRepository;
    private final InsulinDoseRepository insulinDoseRepository;
    private final PatientRepository patientRepository;

    @Override
    public DashboardDTO getDashboardData() {
        // Mock data for now
        var realTimeGlucose = new GlucoseReading();
        realTimeGlucose.setValue(145);
        realTimeGlucose.setTrend("up");

        var glucoseReadings = List.of(
                new GlucoseReading(),
                new GlucoseReading()
        );

        var keyMetrics = KeyMetricsDTO.builder()
                .timeInRange(72)
                .timeBelowRange(4)
                .averageGlucose(165)
                .build();

        return DashboardDTO.builder()
                .realTimeGlucose(realTimeGlucose)
                .glucoseReadings(glucoseReadings)
                .keyMetrics(keyMetrics)
                .build();
    }

    @Override
    public FoodLog logMeal(FoodLog foodLog) {
        return foodLogRepository.save(foodLog);
    }

    @Override
    public InsulinDose logInsulin(InsulinDose insulinDose) {
        return insulinDoseRepository.save(insulinDose);
    }

    @Override
    public PatientDTO getPatient(UUID patientId) {
        return patientRepository.findById(patientId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
    }

    // Helper method to handle mapping
    private PatientDTO mapToDTO(Patient patient) {
        return PatientDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .surName(patient.getSurname())
                .phoneNumbers(patient.getPhoneNumbers())
                .dob(patient.getDob())
                .diagnosisDate(patient.getDiagnosisDate())
                .emergencyContactPhone(patient.getEmergencyContactPhone())
                // Convert Instant (DB) to LocalDateTime (DTO)
                .createdAt(java.time.LocalDateTime.ofInstant(
                        patient.getCreatedAt(), ZoneId.systemDefault()))
                .build();
    }
}
