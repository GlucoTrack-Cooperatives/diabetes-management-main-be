package dm.diabetesmanagementmainbe.service;

import dm.diabetesmanagementmainbe.dao.model.AbstractEntity;
import dm.diabetesmanagementmainbe.dao.repository.AbstractRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class AbstractService<T extends AbstractEntity> {
    protected final AbstractRepository<T> repository;

    public AbstractService(AbstractRepository<T> repository) {
        this.repository = repository;
    }

    public T findById(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + " not found"));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findById(id));
    }
}
