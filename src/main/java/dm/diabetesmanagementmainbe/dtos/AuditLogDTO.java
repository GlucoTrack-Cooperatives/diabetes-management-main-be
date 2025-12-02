package dm.diabetesmanagementmainbe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogDTO {
    private UUID id;
    private String action;
    private String resourceType;
    private UUID resourceId;
    private LocalDateTime timestamp;
    private String ipAddress;
    private String changes;
    private LocalDateTime createdAt;
}
