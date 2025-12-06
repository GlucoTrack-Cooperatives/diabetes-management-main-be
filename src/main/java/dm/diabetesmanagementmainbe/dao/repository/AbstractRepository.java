package dm.diabetesmanagementmainbe.dao.repository;

import dm.diabetesmanagementmainbe.dao.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AbstractRepository<T extends AbstractEntity> extends JpaRepository<T, UUID>, JpaSpecificationExecutor<T> {
}

