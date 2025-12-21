package dm.diabetesmanagementmainbe.dao.repository.stats;

import dm.diabetesmanagementmainbe.dao.model.DailyPatientStat;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyPatientStatRepository extends AbstractRepository<DailyPatientStat> {

    Optional<DailyPatientStat> findByPatientIdAndDate(UUID patientId, LocalDate date);
}
