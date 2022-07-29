package it.academy.events_service.mappers;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventType;
import it.academy.events_service.dto.FilmDtoCreate;
import it.academy.events_service.service.UserHolder;

import java.time.LocalDateTime;
import java.util.UUID;

public class FromFilmDtoCreateToFilmEvent {
    private UserHolder holder;

    public FromFilmDtoCreateToFilmEvent(UserHolder holder) {
        this.holder = holder;
    }

    public FilmEvent convert(FilmDtoCreate filmDto) {

        FilmEvent filmEvent = new FilmEvent();

        filmEvent.setUuid(UUID.randomUUID());
        filmEvent.setDtCreate(LocalDateTime.now());
        filmEvent.setDtUpdate(filmEvent.getDtCreate());
        filmEvent.setTitle(filmDto.getTitle());
        filmEvent.setDescription(filmDto.getDescription());
        filmEvent.setDtEvent(filmDto.getDtEvent());
        filmEvent.setDtEndOfSale(filmDto.getDtEndOfSale());
        filmEvent.setType(EEventType.FILMS);
        filmEvent.setStatus(filmDto.getStatus());
        filmEvent.setReleaseDate(filmDto.getReleaseDate());
        filmEvent.setReleaseYear(filmDto.getReleaseYear());
        filmEvent.setDuration(filmDto.getDuration());
        filmEvent.setCountry(filmDto.getCountry());
        filmEvent.setCreator(holder.getUser().getUsername());

        return filmEvent;
    }
}
