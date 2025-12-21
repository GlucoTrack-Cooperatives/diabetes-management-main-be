package dm.diabetesmanagementmainbe.dao.repository.logging;

import dm.diabetesmanagementmainbe.dao.model.logging.FoodLog;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface FoodLogRepository extends AbstractRepository<FoodLog> {
    List<FoodLog> findByPatientIdAndTimestampBetweenOrderByTimestampDesc(UUID patientId, Instant start, Instant end);
}
