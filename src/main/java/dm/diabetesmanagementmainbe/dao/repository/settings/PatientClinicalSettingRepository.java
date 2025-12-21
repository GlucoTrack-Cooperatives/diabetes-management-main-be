package dm.diabetesmanagementmainbe.dao.repository.settings;

import dm.diabetesmanagementmainbe.dao.model.settings.PatientClinicalSetting;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientClinicalSettingRepository extends AbstractRepository<PatientClinicalSetting> {
    Optional<PatientClinicalSetting> findByPatientId(UUID patientId);
}
