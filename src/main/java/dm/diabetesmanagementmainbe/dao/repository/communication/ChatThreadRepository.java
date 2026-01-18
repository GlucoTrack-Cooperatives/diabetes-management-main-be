package dm.diabetesmanagementmainbe.dao.repository.communication;

import dm.diabetesmanagementmainbe.dao.model.communication.ChatThread;
import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatThreadRepository extends AbstractRepository<ChatThread> {
    List<ChatThread> findByPatientId(UUID patientId);
    List<ChatThread> findByPhysicianId(UUID physicianId);
    boolean existsByPatientAndPhysician(Patient patient, Physician physician);
}
