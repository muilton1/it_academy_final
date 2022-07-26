package it.academy.events_service.dao.api;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IConcertDao extends JpaRepository<ConcertEvent,UUID> {
    ConcertEvent findByUuid(UUID uuid);

    @Query("select u from ConcertEvent u where u.status = ?1 or u.creator = ?2")
    Page<ConcertEvent> findAllByStatusAndCreator(EEventStatus status, String creator, Pageable pageable);

    Page<ConcertEvent> findAllByStatus(EEventStatus status, Pageable pageable);
}
