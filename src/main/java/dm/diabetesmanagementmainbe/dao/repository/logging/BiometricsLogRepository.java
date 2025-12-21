package dm.diabetesmanagementmainbe.dao.repository.logging;

import dm.diabetesmanagementmainbe.dao.model.logging.BiometricsLog;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BiometricsLogRepository extends AbstractRepository<BiometricsLog> {
    Optional<BiometricsLog> findByPatientIdAndDate(UUID patientId, LocalDate date);
}
