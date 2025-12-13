package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.HealthEventRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.LifestyleSummaryDTO;
import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.WaterLogRequest;
import dm.diabetesmanagementmainbe.controller.patient.dto.lifestyle.WeightLogRequest;
import dm.diabetesmanagementmainbe.dao.model.logging.*;
import dm.diabetesmanagementmainbe.dao.repository.logging.*;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LifestyleService {

    private final SleepLogRepository sleepLogRepository;
    private final ActivityLogRepository activityLogRepository;
    private final BiometricsLogRepository biometricsLogRepository;
    private final WaterLogRepository waterLogRepository;
    private final HealthEventRepository healthEventRepository;
    private final PatientRepository patientRepository;

    public LifestyleSummaryDTO getLifestyleSummary(UUID patientId) {
        Instant startOfDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);

        var sleep = sleepLogRepository.findByPatientIdAndStartTimeBetween(patientId, startOfDay.minusSeconds(86400), startOfDay)
                .stream().mapToInt(SleepLog::getDurationMinutes).sum();

        var activity = activityLogRepository.findByPatientIdAndTimestampBetween(patientId, startOfDay, Instant.now())
                .stream().mapToInt(ActivityLog::getSteps).sum();

        var biometrics = biometricsLogRepository.findByPatientIdAndDate(patientId, LocalDate.now()).orElse(new BiometricsLog());

        var water = waterLogRepository.findByPatientIdAndLogTimeBetween(patientId, startOfDay, Instant.now())
                .stream().mapToInt(WaterLog::getGlassesCount).sum();

        return LifestyleSummaryDTO.builder()
                .sleepDurationMinutes(sleep)
                .activitySteps(activity)
                .weight(biometrics.getWeightKg())
                .waterGlasses(water)
                .build();
    }

    public void addWater(UUID patientId, WaterLogRequest request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        var waterLog = new WaterLog();
        waterLog.setPatient(patient);
        waterLog.setGlassesCount(request.getGlassesCount());
        waterLog.setLogTime(Instant.now());

        waterLogRepository.save(waterLog);
    }

    public void logWeight(UUID patientId, WeightLogRequest request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        var biometrics = biometricsLogRepository.findByPatientIdAndDate(patientId, LocalDate.now())
                .orElse(new BiometricsLog());

        biometrics.setPatient(patient);
        biometrics.setDate(LocalDate.now());
        biometrics.setWeightKg(request.getWeight());

        biometricsLogRepository.save(biometrics);
    }

    public void logHealthEvent(UUID patientId, HealthEventRequest request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        var healthEvent = new HealthEvent();
        healthEvent.setPatient(patient);
        healthEvent.setEventType(request.getEventType());
        healthEvent.setTimestamp(Instant.now());

        healthEventRepository.save(healthEvent);
    }
}
