package it.academy.events_service.mappers;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dto.FilmDtoUpdate;

public class FromFilmDtoUpdateToFilmEvent {
    public FilmEvent convert(FilmEvent filmEvent, FilmDtoUpdate filmDtoUpdate) {
        if (filmDtoUpdate.getTitle() != null) {
            filmEvent.setTitle(filmDtoUpdate.getTitle());
        }

        if (filmDtoUpdate.getDescription() != null) {
            filmEvent.setTitle(filmDtoUpdate.getDescription());
        }

        if (filmDtoUpdate.getDtEvent() != null) {
            filmEvent.setDtEvent(filmDtoUpdate.getDtEvent());
        }

        if (filmDtoUpdate.getDtEndOfSale() != null) {
            filmEvent.setDtEndOfSale(filmDtoUpdate.getDtEndOfSale());
        }

        if (filmDtoUpdate.getStatus() != null) {
            filmEvent.setStatus(filmDtoUpdate.getStatus());
        }

        if (filmDtoUpdate.getReleaseDate() != null) {
            filmEvent.setReleaseDate(filmDtoUpdate.getReleaseDate());
        }

        if (filmDtoUpdate.getReleaseYear() != 0) {
            filmEvent.setReleaseYear(filmDtoUpdate.getReleaseYear());
        }

        if (filmDtoUpdate.getDuration() != 0) {
            filmEvent.setDuration(filmDtoUpdate.getDuration());
        }

        if (filmDtoUpdate.getCountry() != null) {
            filmEvent.setCountry(filmDtoUpdate.getCountry());
        }

        if (filmDtoUpdate.getCreator() != null) {
            filmEvent.setCreator(filmDtoUpdate.getCreator());
        }

        return filmEvent;
    }
}
