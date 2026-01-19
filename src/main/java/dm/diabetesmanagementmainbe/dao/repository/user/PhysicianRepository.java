package dm.diabetesmanagementmainbe.dao.repository.user;

import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicianRepository extends AbstractRepository<Physician> {

    Optional<Physician> findByEmail(String email);

    @Query("SELECT ph FROM Physician ph LEFT JOIN FETCH ph.patients WHERE ph.id = :physicianId")
    Optional<Physician> findByIdWithPatients(@Param("physicianId") UUID physicianId);
}

