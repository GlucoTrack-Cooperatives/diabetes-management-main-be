package dm.diabetesmanagementmainbe.dao.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "patient")
public class Patient extends User {

    @Size(max = 255)
    @NotNull
    @Column(name = "phone_numbers", nullable = false)
    private String phoneNumbers;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NotNull
    @Column(name = "diagnosis_date", nullable = false)
    private LocalDate diagnosisDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "emergency_contact_phone", nullable = false)
    private String emergencyContactPhone;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "dexcom_email")
    private String dexcomEmail;

    @Column(name = "dexcom_password")
    private String dexcomPassword;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}
