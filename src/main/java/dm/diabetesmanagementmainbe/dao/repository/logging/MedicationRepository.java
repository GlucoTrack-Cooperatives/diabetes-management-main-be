package dm.diabetesmanagementmainbe.dao.repository.logging;

import dm.diabetesmanagementmainbe.dao.model.logging.Medication;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends AbstractRepository<Medication> {
}
