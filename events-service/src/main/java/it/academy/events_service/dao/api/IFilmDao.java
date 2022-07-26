package it.academy.events_service.dao.api;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IFilmDao extends JpaRepository<FilmEvent, UUID> {
    FilmEvent findByUuid(UUID uuid);

    @Query("select u from FilmEvent u where u.status = ?1 or u.creator = ?2")
    Page<FilmEvent> findAllByStatusAndCreator(EEventStatus status, String creator, Pageable pageable);

    Page<FilmEvent> findAllByStatus(EEventStatus status, Pageable pageable);
}
