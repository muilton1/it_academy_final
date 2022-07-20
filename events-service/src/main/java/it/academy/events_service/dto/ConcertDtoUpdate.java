package it.academy.events_service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.academy.events_service.controller.utils.json.LocalDateTimeDeserializer;
import it.academy.events_service.controller.utils.json.LocalDateTimeSerializer;
import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Validated
public class ConcertDtoUpdate {
    private String title;
    private String description;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dtEvent;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dtEndOfSale;

    private EEventStatus status;

    private UUID category;

    public ConcertDtoUpdate() {
    }

    public ConcertDtoUpdate(ConcertEvent entity) {
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.dtEvent = entity.getDtEvent();
        this.dtEndOfSale = entity.getDtEndOfSale();
        this.status = entity.getStatus();
        this.category= entity.getCategory();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDtEvent() {
        return dtEvent;
    }

    public void setDtEvent(LocalDateTime dtEvent) {
        this.dtEvent = dtEvent;
    }

    public LocalDateTime getDtEndOfSale() {
        return dtEndOfSale;
    }

    public void setDtEndOfSale(LocalDateTime dtEndOfSale) {
        this.dtEndOfSale = dtEndOfSale;
    }

    public EEventStatus getStatus() {
        return status;
    }

    public void setStatus(EEventStatus status) {
        this.status = status;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ConcertDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dtEvent=" + dtEvent +
                ", dtEndOfSale=" + dtEndOfSale +
                ", status=" + status +
                ", category=" + category +
                '}';
    }
}
