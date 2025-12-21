package dm.diabetesmanagementmainbe.dao.model.tracker;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "glucose_reading")
public class GlucoseReading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @Column(name = "\"timestamp\"", nullable = false)
    private Instant timestamp;

    @NotNull
    @Column(name = "value", nullable = false)
    private Integer value;

    @Size(max = 50)
    @NotNull
    @Column(name = "trend", nullable = false, length = 50)
    private String trend;

    @Size(max = 50)
    @NotNull
    @Column(name = "source", nullable = false, length = 50)
    private String source;

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
