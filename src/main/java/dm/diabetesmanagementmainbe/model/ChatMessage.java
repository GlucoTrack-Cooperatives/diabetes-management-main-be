package dm.diabetesmanagementmainbe.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "chat_messages", indexes = {
    @Index(name = "idx_thread_id", columnList = "thread_id"),
    @Index(name = "idx_sender_id", columnList = "sender_id"),
    @Index(name = "idx_timestamp", columnList = "timestamp"),
    @Index(name = "idx_is_read", columnList = "is_read")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Column(name = "is_read")
    @Builder.Default
    private Boolean isRead = false;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
