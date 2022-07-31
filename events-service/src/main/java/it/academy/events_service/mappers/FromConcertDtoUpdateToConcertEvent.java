package it.academy.events_service.mappers;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dto.ConcertDtoUpdate;

public class FromConcertDtoUpdateToConcertEvent {
    public ConcertEvent convert(ConcertDtoUpdate concertDtoUpdate, ConcertEvent concertEvent) {
        if (concertDtoUpdate.getTitle() != null) {
            concertEvent.setTitle(concertDtoUpdate.getTitle());
        }

        if (concertDtoUpdate.getDescription() != null) {
            concertEvent.setDescription(concertDtoUpdate.getDescription());
        }

        if (concertDtoUpdate.getDtEvent() != null) {
            concertEvent.setDtEvent(concertDtoUpdate.getDtEvent());
        }

        if (concertDtoUpdate.getDtEndOfSale() != null) {
            concertEvent.setDtEndOfSale(concertDtoUpdate.getDtEndOfSale());
        }

        if (concertDtoUpdate.getStatus() != null) {
            concertEvent.setStatus(concertDtoUpdate.getStatus());
        }

        if (concertDtoUpdate.getCategory() != null) {
            concertEvent.setCategory(concertDtoUpdate.getCategory());
        }

        if (concertDtoUpdate.getCreator() != null) {
            concertEvent.setCreator(concertDtoUpdate.getCreator());
        }

        return concertEvent;
    }
}
