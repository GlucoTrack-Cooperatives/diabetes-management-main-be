package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.settings.PatientSettingsDTO;
import dm.diabetesmanagementmainbe.dao.model.settings.PatientClinicalSetting;
import dm.diabetesmanagementmainbe.dao.repository.settings.PatientClinicalSettingRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final PatientClinicalSettingRepository settingsRepository;
    private final PatientRepository patientRepository;

    public PatientSettingsDTO getPatientSettings(UUID patientId) {
        return settingsRepository.findByPatientId(patientId)
                .map(settings -> PatientSettingsDTO.builder()
                        .targetRangeHigh(settings.getTargetRangeHigh())
                        .targetRangeLow(settings.getTargetRangeLow())
                        .insulinCarbRatio(settings.getInsulinCarbRatio())
                        .correctionFactor(settings.getCorrectionFactor())
                        .lowThreshold(settings.getLowThreshold())
                        .criticalLowThreshold(settings.getCriticalLowThreshold())
                        .highThreshold(settings.getHighThreshold())
                        .criticalHighThreshold(settings.getCriticalHighThreshold())
                        .build())
                .orElse(new PatientSettingsDTO());
    }

    public void updatePatientSettings(UUID patientId, PatientSettingsDTO request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        var settings = settingsRepository.findByPatientId(patientId)
                .orElse(new PatientClinicalSetting());

        settings.setPatient(patient);

        if (request.getTargetRangeHigh() != null) {
            validatePositiveValue(request.getTargetRangeHigh(), "Target Range High");
            settings.setTargetRangeHigh(request.getTargetRangeHigh());
        }

        if (request.getTargetRangeLow() != null) {
            validatePositiveValue(request.getTargetRangeLow(), "Target Range Low");
            settings.setTargetRangeLow(request.getTargetRangeLow());
        }

        if (request.getInsulinCarbRatio() != null) {
            validatePositiveValue(request.getInsulinCarbRatio(), "Insulin Carb Ratio");
            settings.setInsulinCarbRatio(request.getInsulinCarbRatio());
        }

        if (request.getCorrectionFactor() != null) {
            validatePositiveValue(request.getCorrectionFactor(), "Correction Factor");
            settings.setCorrectionFactor(request.getCorrectionFactor());
        }

        if (request.getLowThreshold() != null) {
            validatePositiveValue(request.getLowThreshold(), "Low Threshold");
            settings.setLowThreshold(request.getLowThreshold());
        }

        if (request.getCriticalLowThreshold() != null) {
            validatePositiveValue(request.getCriticalLowThreshold(), "Critical Low Threshold");
            settings.setCriticalLowThreshold(request.getCriticalLowThreshold());
        }

        if (request.getHighThreshold() != null) {
            validatePositiveValue(request.getHighThreshold(), "High Threshold");
            settings.setHighThreshold(request.getHighThreshold());
        }

        if (request.getCriticalHighThreshold() != null) {
            validatePositiveValue(request.getCriticalHighThreshold(), "Critical High Threshold");
            settings.setCriticalHighThreshold(request.getCriticalHighThreshold());
        }

        settingsRepository.save(settings);
    }

    private void validatePositiveValue(Number value, String fieldName) {
        if (value.doubleValue() <= 0) {
            throw new IllegalArgumentException(fieldName + " must be a positive value");
        }
    }
}
