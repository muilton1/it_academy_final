package it.academy.classifier_service.dao.api;

import it.academy.classifier_service.dao.entity.ConcertCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IConcertCategoryDao extends JpaRepository<ConcertCategory, UUID> {
    ConcertCategory findByUuid(UUID uuid);
}
