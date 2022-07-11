package it.academy.events_service.dao.api;

import it.academy.events_service.dao.entity.FilmEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IFilmDao extends JpaRepository<FilmEvent,Long> {
    FilmEvent findByUuid(UUID uuid);
}
