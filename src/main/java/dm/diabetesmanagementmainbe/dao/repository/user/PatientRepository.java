package dm.diabetesmanagementmainbe.dao.repository.user;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends AbstractRepository<Patient> {

    Optional<Patient> findByEmail(String email);

    // When need to get patients by physician
    @Query("SELECT p FROM Patient p WHERE p.physician.id = :physicianId")
    List<Patient> findByPhysicianId(@Param("physicianId") UUID physicianId);

    // WHen need to get patients with confirmed physicians
    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.physician WHERE p.physician.id = :physicianId AND p.isPhysicianConfirmed = true")
    List<Patient> findConfirmedPatientsByPhysician(@Param("physicianId") UUID physicianId);
}