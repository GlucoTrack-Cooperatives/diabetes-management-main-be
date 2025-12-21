package dm.diabetesmanagementmainbe.dao.repository.communication;

import dm.diabetesmanagementmainbe.dao.model.communication.Alert;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlertRepository extends AbstractRepository<Alert> {
    List<Alert> findByPatientIdAndIsAcknowledgedFalse(UUID patientId);
}
