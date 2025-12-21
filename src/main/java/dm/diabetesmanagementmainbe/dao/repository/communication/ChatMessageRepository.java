package dm.diabetesmanagementmainbe.dao.repository.communication;

import dm.diabetesmanagementmainbe.dao.model.communication.ChatMessage;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends AbstractRepository<ChatMessage> {
    List<ChatMessage> findByChatThreadIdOrderByTimestampDesc(UUID chatThreadId);
}
