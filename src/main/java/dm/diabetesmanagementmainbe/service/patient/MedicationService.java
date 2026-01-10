package dm.diabetesmanagementmainbe.service.patient;

import dm.diabetesmanagementmainbe.controller.patient.dto.log.MedicationDTO;
import dm.diabetesmanagementmainbe.dao.repository.logging.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;

    public List<MedicationDTO> getAllMedications() {
        return medicationRepository.findAll().stream()
                .map(med -> MedicationDTO.builder()
                        .id(med.getId())
                        .name(med.getName())
                        .type(med.getType())
                        .build())
                .collect(Collectors.toList());
    }
}