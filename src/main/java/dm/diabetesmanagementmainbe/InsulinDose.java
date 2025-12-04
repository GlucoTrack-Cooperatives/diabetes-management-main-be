package dm.diabetesmanagementmainbe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "insulin_dose")
public class InsulinDose {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "\"timestamp\"", nullable = false)
    private Instant timestamp;

    @Size(max = 50)
    @NotNull
    @Column(name = "insulin_type", nullable = false, length = 50)
    private String insulinType;

    @NotNull
    @Column(name = "units", nullable = false)
    private Float units;

    @ColumnDefault("false")
    @Column(name = "is_correction")
    private Boolean isCorrection;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}