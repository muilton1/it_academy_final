package it.academy.events_service.service.api;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dto.ConcertDto;
import it.academy.events_service.dto.PageContent;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IConcertService {
    ConcertEvent create(@Valid ConcertDto concertDto);

    PageContent getAll(Integer pageNo, Integer pageSize);

    ConcertEvent update(ConcertDto concertDto, UUID uuid, LocalDateTime lastKnowDtUpdate);

    ConcertEvent getOne(UUID uuid);
}
