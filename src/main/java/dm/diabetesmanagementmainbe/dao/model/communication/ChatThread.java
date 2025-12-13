package dm.diabetesmanagementmainbe.dao.model.communication;

import dm.diabetesmanagementmainbe.dao.model.user.Patient;
import dm.diabetesmanagementmainbe.dao.model.user.Physician;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "chat_thread")
public class ChatThread {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;

    @OneToMany(mappedBy = "chatThread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> messages;

    @Column(name = "last_message_at")
    private Instant lastMessageAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
