package dm.diabetesmanagementmainbe.dao.repository.tracker;

import dm.diabetesmanagementmainbe.dao.model.tracker.GlucoseReading;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GlucoseReadingRepository extends AbstractRepository<GlucoseReading> {

    Optional<GlucoseReading> findFirstByPatientIdOrderByTimestampDesc(UUID patientId);

    List<GlucoseReading> findByPatientIdAndTimestampBetween(UUID patientId, Instant start, Instant end);

    /**
     * Optimized query to fetch the latest glucose reading for a list of patients.
     * It finds the reading where the (patient_id, timestamp) matches the (patient_id, max_timestamp) for that patient.
     */
    @Query("SELECT gr FROM GlucoseReading gr WHERE (gr.patient.id, gr.timestamp) IN " +
            "(SELECT sub.patient.id, MAX(sub.timestamp) FROM GlucoseReading sub " +
            "WHERE sub.patient.id IN :patientIds GROUP BY sub.patient.id)")
    List<GlucoseReading> findLatestReadingsForPatients(@Param("patientIds") List<UUID> patientIds);
}
