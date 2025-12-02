package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "chat_threads", indexes = {
    @Index(name = "idx_patient_id", columnList = "patient_id"),
    @Index(name = "idx_physician_id", columnList = "physician_id"),
    @Index(name = "idx_last_message_at", columnList = "last_message_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatThread {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "last_message_at")
    private LocalDateTime lastMessageAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
}
