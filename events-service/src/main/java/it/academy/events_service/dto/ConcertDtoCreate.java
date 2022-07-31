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
public class ConcertDtoCreate {
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
    private String creator;

    public ConcertDtoCreate() {
    }

    public ConcertDtoCreate(ConcertEvent entity) {
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.dtEvent = entity.getDtEvent();
        this.dtEndOfSale = entity.getDtEndOfSale();
        this.status = entity.getStatus();
        this.category = entity.getCategory();
        this.creator = entity.getCreator();
    }

    @NotBlank(message = "Заполните название концерта!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "Заполните описание концерта!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Заполните дату концерта!")
    public LocalDateTime getDtEvent() {
        return dtEvent;
    }

    public void setDtEvent(LocalDateTime dtEvent) {
        this.dtEvent = dtEvent;
    }

    @NotNull(message = "Заполните дату окончания продажи билетов на концерт!")
    public LocalDateTime getDtEndOfSale() {
        return dtEndOfSale;
    }

    public void setDtEndOfSale(LocalDateTime dtEndOfSale) {
        this.dtEndOfSale = dtEndOfSale;
    }

    @NotNull(message = "Заполните статус концерта!")
    public EEventStatus getStatus() {
        return status;
    }

    public void setStatus(EEventStatus status) {
        this.status = status;
    }

    @NotNull(message = "Заполните категорию концерта!")
    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
