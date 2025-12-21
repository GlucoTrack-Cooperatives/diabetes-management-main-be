package dm.diabetesmanagementmainbe.dao.repository.logging;

import dm.diabetesmanagementmainbe.dao.model.logging.InsulinDose;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface InsulinDoseRepository extends AbstractRepository<InsulinDose> {
    List<InsulinDose> findByPatientIdAndTimestampBetweenOrderByTimestampDesc(UUID patientId, Instant start, Instant end);
}
