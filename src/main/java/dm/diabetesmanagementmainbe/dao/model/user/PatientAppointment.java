package dm.diabetesmanagementmainbe.dao.model.user;

import dm.diabetesmanagementmainbe.dao.model.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "patient_appointment")
public class PatientAppointment extends AbstractEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AppointmentType type;

    @NotNull
    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @NotNull
    @Column(name = "next_due_date", nullable = false)
    private LocalDateTime nextDueDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
