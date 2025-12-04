package dm.diabetesmanagementmainbe;

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
@Table(name = "physician", uniqueConstraints = {
        @UniqueConstraint(name = "physician_license_number_key", columnNames = {"license_number"})
})
public class Physician {
    @Id
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 255)
    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(max = 255)
    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

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

}