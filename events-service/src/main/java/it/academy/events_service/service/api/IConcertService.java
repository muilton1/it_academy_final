package it.academy.events_service.service.api;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dto.ConcertDtoCreate;
import it.academy.events_service.dto.ConcertDtoUpdate;
import it.academy.events_service.dto.PageContent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IConcertService {
    void create(@Valid ConcertDtoCreate concertDto);

    PageContent getAll(Integer pageNo, Integer pageSize);

    void update(ConcertDtoUpdate concertDto, UUID uuid, LocalDateTime lastKnowDtUpdate);

    ConcertEvent getOne(UUID uuid);
}
