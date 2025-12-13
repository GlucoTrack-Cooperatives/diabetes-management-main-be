package dm.diabetesmanagementmainbe.dao.repository.logging;

import dm.diabetesmanagementmainbe.dao.model.logging.ActivityLog;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityLogRepository extends AbstractRepository<ActivityLog> {
    List<ActivityLog> findByPatientIdAndTimestampBetween(UUID patientId, Instant start, Instant end);
}
