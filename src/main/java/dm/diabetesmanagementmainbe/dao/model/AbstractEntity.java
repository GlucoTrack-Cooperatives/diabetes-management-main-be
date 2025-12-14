package dm.diabetesmanagementmainbe.dao.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Column(
            updatable = false,
            nullable = false
    )
    private UUID id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "creation_timestamp",
            updatable = false
    )
    private Date creationTimestamp;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "modification_timestamp"
    )
    private Date modificationTimestamp;

    public AbstractEntity() {
    }

    @PrePersist
    public void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }

        this.creationTimestamp = new Date();
        this.modificationTimestamp = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        this.modificationTimestamp = new Date();
    }

}