package it.academy.events_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.academy.events_service.controller.utils.json.LocalDateTimeDeserializer;
import it.academy.events_service.controller.utils.json.LocalDateTimeSerializer;
import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Validated
public class FilmDtoUpdate {

    private String title;
    private String description;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dtEvent;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dtEndOfSale;
    private EEventStatus status;
    private int releaseYear;
    @JsonFormat(pattern = "dd MMMM yyyy")
    private LocalDate releaseDate;
    private UUID country;
    private int duration;

    public FilmDtoUpdate() {
    }

    public FilmDtoUpdate(FilmEvent entity) {
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.dtEvent = entity.getDtEvent();
        this.dtEndOfSale = entity.getDtEndOfSale();
        this.status = entity.getStatus();
        this.releaseYear = entity.getReleaseYear();
        this.releaseDate = entity.getReleaseDate();
        this.country = entity.getCountry();
        this.duration = entity.getDuration();
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
