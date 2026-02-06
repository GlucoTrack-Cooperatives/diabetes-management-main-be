package dm.diabetesmanagementmainbe.service.physician;

import dm.diabetesmanagementmainbe.controller.physician.dto.CreateAppointmentRequest;
import dm.diabetesmanagementmainbe.dao.model.user.AppointmentType;
import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.model.user.PatientAppointment;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientAppointmentRepository;
import dm.diabetesmanagementmainbe.dao.repository.user.PatientRepository;
import dm.diabetesmanagementmainbe.dtos.PatientAppointmentDTO;
import dm.diabetesmanagementmainbe.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final PatientAppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public List<PatientAppointmentDTO> getPatientAppointments(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientAppointmentDTO createAppointment(UUID patientId, CreateAppointmentRequest request) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        AppointmentType type = AppointmentType.fromLabel(request.getType());
        
        PatientAppointment appointment = new PatientAppointment();
        appointment.setPatient(patient);
        appointment.setType(type);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setNotes(request.getNotes());
        
        // Calculate next due date based on frequency
        LocalDateTime nextDueDate = request.getAppointmentDate().plus(type.getFrequency());
        appointment.setNextDueDate(nextDueDate);

        PatientAppointment savedAppointment = appointmentRepository.save(appointment);
        return toDTO(savedAppointment);
    }

    private PatientAppointmentDTO toDTO(PatientAppointment entity) {
        return PatientAppointmentDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatient().getId())
                .type(entity.getType().getLabel())
                .appointmentDate(entity.getAppointmentDate())
                .nextDueDate(entity.getNextDueDate())
                .notes(entity.getNotes())
                .build();
    }
}
