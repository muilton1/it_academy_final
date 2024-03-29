package it.academy.events_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.academy.events_service.controller.utils.json.LocalDateTimeDeserializer;
import it.academy.events_service.controller.utils.json.LocalDateTimeSerializer;
import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
    private String creator;

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
        this.creator = entity.getCreator();
    }

    @NotBlank(message = "Заполните название фильма!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank(message = "Заполните описание фильма!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Заполните дату фильма!")
    public LocalDateTime getDtEvent() {
        return dtEvent;
    }

    public void setDtEvent(LocalDateTime dtEvent) {
        this.dtEvent = dtEvent;
    }

    @NotNull(message = "Заполните конец продажи билетов на фильм!")
    public LocalDateTime getDtEndOfSale() {
        return dtEndOfSale;
    }

    public void setDtEndOfSale(LocalDateTime dtEndOfSale) {
        this.dtEndOfSale = dtEndOfSale;
    }

    @NotNull(message = "Заполните статус фильма!")
    public EEventStatus getStatus() {
        return status;
    }

    public void setStatus(EEventStatus status) {
        this.status = status;
    }

    @NotNull(message = "Заполните дату выпуска фильма!")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NotNull(message = "Заполните страну фильма!")
    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }

    @Min(value = 1895, message = "Год выпуска должен быть не меньше 1895!")
    @Max(value = 2024, message = "Год выпуска должен быть не больше 2024!")
    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Min(value = 1, message = "Длительность фильма должна быть больше 1 минуты!")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
