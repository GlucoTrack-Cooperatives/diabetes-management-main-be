package dm.diabetesmanagementmainbe.dao.repository.logging;

import dm.diabetesmanagementmainbe.dao.model.logging.WaterLog;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface WaterLogRepository extends AbstractRepository<WaterLog> {
    List<WaterLog> findByPatientIdAndLogTimeBetween(UUID patientId, Instant start, Instant end);
}
