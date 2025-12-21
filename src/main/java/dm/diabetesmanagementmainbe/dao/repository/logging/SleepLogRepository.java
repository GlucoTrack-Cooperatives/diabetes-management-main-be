package dm.diabetesmanagementmainbe.dao.repository.logging;

import dm.diabetesmanagementmainbe.dao.model.logging.SleepLog;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface SleepLogRepository extends AbstractRepository<SleepLog> {
    List<SleepLog> findByPatientIdAndStartTimeBetween(UUID patientId, Instant start, Instant end);
}
