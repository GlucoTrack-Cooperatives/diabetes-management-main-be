package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.log.FoodLogRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.InsulinDoseRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.log.LogEntryDTO;
import dm.diabetesmanagementmainbe.dao.model.logging.FoodLog;
import dm.diabetesmanagementmainbe.dao.model.logging.InsulinDose;
import dm.diabetesmanagementmainbe.dao.repository.logging.FoodLogRepository;
import dm.diabetesmanagementmainbe.dao.repository.logging.InsulinDoseRepository;
import dm.diabetesmanagementmainbe.dao.repository.logging.MedicationRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LogService {

    private final FoodLogRepository foodLogRepository;
    private final InsulinDoseRepository insulinDoseRepository;
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;

    public void createFoodLog(UUID patientId, FoodLogRequest request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        var foodLog = new FoodLog();
        foodLog.setPatient(patient);
        foodLog.setCarbs(request.getCarbs());
        foodLog.setCalories(request.getCalories());
        foodLog.setDescription(request.getDescription());
        foodLog.setImageUrl(request.getImageUrl());
        foodLog.setTimestamp(Instant.now());

        foodLogRepository.save(foodLog);
    }

    public void createInsulinDose(UUID patientId, InsulinDoseRequest request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        var medication = medicationRepository.findById(request.getMedicationId())
                .orElseThrow(() -> new RuntimeException("Medication not found"));

        var insulinDose = new InsulinDose();
        insulinDose.setPatient(patient);
        insulinDose.setMedication(medication);
        insulinDose.setUnits(request.getUnits());
        insulinDose.setTimestamp(Instant.now());

        insulinDoseRepository.save(insulinDose);
    }

    public List<LogEntryDTO> findRecentLogs(UUID patientId) {
        Instant end = Instant.now();
        Instant start = end.minus(1, ChronoUnit.DAYS);

        var foodLogs = foodLogRepository.findByPatientIdAndTimestampBetweenOrderByTimestampDesc(patientId, start, end)
                .stream()
                .map(log -> LogEntryDTO.builder()
                        .type("Food")
                        .timestamp(log.getTimestamp())
                        .description(log.getCarbs() + "g Carbs")
                        .build());

        var insulinLogs = insulinDoseRepository.findByPatientIdAndTimestampBetweenOrderByTimestampDesc(patientId, start, end)
                .stream()
                .map(log -> LogEntryDTO.builder()
                        .type("Insulin")
                        .timestamp(log.getTimestamp())
                        .description(log.getUnits() + " Units " + log.getMedication().getName())
                        .build());

        return Stream.concat(foodLogs, insulinLogs)
                .sorted(Comparator.comparing(LogEntryDTO::getTimestamp).reversed())
                .toList();
    }
}
