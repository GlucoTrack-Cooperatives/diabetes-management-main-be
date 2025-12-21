package dm.diabetesmanagementmainbe.dao.repository.tracker;

import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GlucoseReadingRepository extends AbstractRepository<GlucoseReading> {

    Optional<GlucoseReading> findFirstByPatientIdOrderByTimestampDesc(UUID patientId);

    List<GlucoseReading> findByPatientIdAndTimestampBetween(UUID patientId, Instant start, Instant end);
}
