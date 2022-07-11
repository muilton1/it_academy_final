package it.academy.events_service.dto;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
@Validated
public class ConcertDto {
    private String title;
    private String description;

    private LocalDateTime dtEvent;

    private LocalDateTime dtEndOfSale;

    private EEventStatus status;

    private UUID category;

    public ConcertDto() {
    }

    public ConcertDto(ConcertEvent entity) {
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.dtEvent = entity.getDtEvent();
        this.dtEndOfSale = entity.getDtEndOfSale();
        this.status = entity.getStatus();
        this.category= entity.getCategory();
    }
    @NotBlank(message = "Заполните название концерта!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @NotBlank(message = "Заполните описание концерта!")
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
