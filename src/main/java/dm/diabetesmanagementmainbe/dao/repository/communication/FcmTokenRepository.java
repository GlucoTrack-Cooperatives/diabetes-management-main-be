package dm.diabetesmanagementmainbe.dao.repository.communication;

import dm.diabetesmanagementmainbe.dao.model.communication.FcmToken;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FcmTokenRepository extends AbstractRepository<FcmToken> {
    List<FcmToken> findByUserId(UUID userId);
}
