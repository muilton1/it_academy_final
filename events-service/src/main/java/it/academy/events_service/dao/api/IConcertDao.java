package it.academy.events_service.dao.api;

import it.academy.events_service.dao.entity.ConcertEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IConcertDao extends JpaRepository<ConcertEvent,Long> {
    ConcertEvent findByUuid(UUID uuid);
}
