package it.academy.events_service.mappers;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dao.enums.EEventType;
import it.academy.events_service.dto.ConcertDtoCreate;
import it.academy.events_service.service.UserHolder;

import java.time.LocalDateTime;
import java.util.UUID;

public class FromConcertDtoCreateToConcertEvent {
    private UserHolder holder;

    public FromConcertDtoCreateToConcertEvent(UserHolder holder) {
        this.holder = holder;
    }

    public ConcertEvent convert(ConcertDtoCreate concertDto) {
        ConcertEvent concertEvent = new ConcertEvent();

        concertEvent.setUuid(UUID.randomUUID());
        concertEvent.setDtCreate(LocalDateTime.now());
        concertEvent.setDtUpdate(concertEvent.getDtCreate());
        concertEvent.setTitle(concertDto.getTitle());
        concertEvent.setDescription(concertDto.getDescription());
        concertEvent.setDtEvent(concertDto.getDtEvent());
        concertEvent.setDtEndOfSale(concertDto.getDtEndOfSale());
        concertEvent.setType(EEventType.CONCERTS);
        concertEvent.setStatus(concertDto.getStatus());
        concertEvent.setCategory(concertDto.getCategory());
        concertEvent.setCreator(holder.getUser().getUsername());

        return concertEvent;
    }
}
