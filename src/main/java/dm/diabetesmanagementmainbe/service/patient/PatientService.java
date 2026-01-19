package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.settings.UpdatePatientProfileRequest;
import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.model.user.Physician;
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
import org.springframework.transaction.annotation.Transactional;
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
    private final CommunicationService communicationService;


    @Override
    public FoodLog logMeal(FoodLog foodLog) {
        return foodLogRepository.save(foodLog);
    }

    @Override
    public InsulinDose logInsulin(InsulinDose insulinDose) {
        return insulinDoseRepository.save(insulinDose);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientDTO getPatient(UUID patientId) {
        return patientRepository.findById(patientId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));
    }

    public void updatePatientProfile(UUID patientId, UpdatePatientProfileRequest request) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (request.getFirstName() != null) patient.setFirstName(request.getFirstName());
        if (request.getSurName() != null) patient.setSurname(request.getSurName());
        if (request.getPhoneNumber() != null) patient.setPhoneNumbers(request.getPhoneNumber());
        if (request.getDob() != null) patient.setDob(request.getDob());
        if (request.getDiagnosisDate() != null) patient.setDiagnosisDate(request.getDiagnosisDate());
        if (request.getEmergencyContactPhone() != null) patient.setEmergencyContactPhone(request.getEmergencyContactPhone());

        patientRepository.save(patient);
    }

    @Transactional
    public void confirmPhysician(UUID patientId) {
        var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (patient.getPhysician() == null) {
            throw new RuntimeException("No physician request pending");
        }

        if (Boolean.TRUE.equals(patient.getIsPhysicianConfirmed())) {
            // log.warn("Patient {} already confirmed physician {}", patientId, patient.getPhysician().getId());
            return; // Already confirmed, no need to do anything
        }

        patient.setIsPhysicianConfirmed(true);
        patientRepository.save(patient);

        // Create initial chat thread
        communicationService.createInitialThread(patient, patient.getPhysician());
    }

    // Helper method to handle mapping
    private PatientDTO mapToDTO(Patient patient) {
        PatientDTO.PatientDTOBuilder builder = PatientDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .surName(patient.getSurname())
                .phoneNumbers(patient.getPhoneNumbers())
                .dob(patient.getDob())
                .diagnosisDate(patient.getDiagnosisDate())
                .emergencyContactPhone(patient.getEmergencyContactPhone())
                .createdAt(java.time.LocalDateTime.ofInstant(
                        patient.getCreatedAt(), ZoneId.systemDefault()));

        // ADD PHYSICIAN FIELDS
        if (patient.getPhysician() != null) {
            Physician physician = patient.getPhysician();
            builder.physicianId(physician.getId())
                    .physicianName(physician.getFirstName() + " " + physician.getSurname())
                    .physicianSpecialty(physician.getSpecialty())  // if this field exists
                    .physicianClinic(physician.getClinicName());        // if this field exists
        }

        builder.isPhysicianConfirmed(patient.getIsPhysicianConfirmed());

        return builder.build();
    }
}
