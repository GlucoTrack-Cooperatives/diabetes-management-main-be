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
                        .build())
                .orElse(new PatientSettingsDTO());
    }

    public void updatePatientSettings(UUID patientId, PatientSettingsDTO request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        var settings = settingsRepository.findByPatientId(patientId)
                .orElse(new PatientClinicalSetting());

        settings.setPatient(patient);
        settings.setTargetRangeHigh(request.getTargetRangeHigh());
        settings.setTargetRangeLow(request.getTargetRangeLow());
        settings.setInsulinCarbRatio(request.getInsulinCarbRatio());
        settings.setCorrectionFactor(request.getCorrectionFactor());

        settingsRepository.save(settings);
    }
}
