package it.academy.events_service.service.api;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dto.FilmDtoCreate;
import it.academy.events_service.dto.FilmDtoUpdate;
import it.academy.events_service.dto.PageContent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IFilmService {
    void create(@Valid FilmDtoCreate filmDto);

    PageContent getAll(Integer pageNo, Integer pageSize);

    void update(FilmDtoUpdate filmDtoUpdate, UUID uuid, LocalDateTime lastKnowDtUpdate);

    FilmEvent getOne(UUID uuid);
}
