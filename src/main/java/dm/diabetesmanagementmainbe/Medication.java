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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 255)
    @NotNull
    @Column(name = "medication_name", nullable = false)
    private String medicationName;

    @NotNull
    @Column(name = "dosage_mg", nullable = false)
    private Float dosageMg;

    @Size(max = 50)
    @NotNull
    @Column(name = "frequency", nullable = false, length = 50)
    private String frequency;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "side_effects", length = Integer.MAX_VALUE)
    private String sideEffects;

    @Column(name = "interaction_warnings")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> interactionWarnings;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}