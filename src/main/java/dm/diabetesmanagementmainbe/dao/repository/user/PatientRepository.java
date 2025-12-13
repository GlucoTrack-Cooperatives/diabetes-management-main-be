package dm.diabetesmanagementmainbe.dao.repository.user;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends AbstractRepository<Patient> {

    Optional<Patient> findByEmail(String email);
}
