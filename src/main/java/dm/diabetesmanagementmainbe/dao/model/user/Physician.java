package dm.diabetesmanagementmainbe.dao.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "physician", uniqueConstraints = {
        @UniqueConstraint(name = "physician_license_number_key", columnNames = {"license_number"})
})
public class Physician extends User {

    // DOCTOR Specialty: What is that doctor specialist for
    @Size(max = 255)
    @NotNull
    @Column(name = "specialty", nullable = false)
    private String specialty;

    @Size(max = 255)
    @NotNull
    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "clinic_name", nullable = false)
    private String clinicName;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

}
