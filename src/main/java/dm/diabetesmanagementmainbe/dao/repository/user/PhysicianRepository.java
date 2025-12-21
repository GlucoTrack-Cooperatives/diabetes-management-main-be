package dm.diabetesmanagementmainbe.dao.repository.user;

import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicianRepository extends AbstractRepository<Physician> {

    Optional<Physician> findByEmail(String email);
}
