package it.academy.events_service.service.api;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dto.FilmDto;
import it.academy.events_service.dto.PageContent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IFilmService {
    FilmEvent create(@Valid FilmDto filmDto);

    PageContent getAll(Integer pageNo, Integer pageSize);

    FilmEvent update(FilmDto filmDto, UUID uuid, LocalDateTime lastKnowDtUpdate);

    FilmEvent getOne(UUID uuid);
}
