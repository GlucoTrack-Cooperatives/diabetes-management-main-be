package dm.diabetesmanagementmainbe.dao.repository.user;

import dm.diabetesmanagementmainbe.dao.model.user.PatientAppointment;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientAppointmentRepository extends AbstractRepository<PatientAppointment> {
    
    @Query("SELECT pa FROM PatientAppointment pa WHERE pa.patient.id = :patientId ORDER BY pa.appointmentDate DESC")
    List<PatientAppointment> findByPatientId(@Param("patientId") UUID patientId);
}
