package it.academy.classifier_service.dto;

import it.academy.classifier_service.dao.entity.Country;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
@Validated
public class CountryDto {
    private String title;
    private String description;

    public CountryDto() {
    }

    public CountryDto(Country entity) {
        this.title = entity.getTitle();
        this.description = entity.getDescription();
    }
    @NotBlank(message = "Заполните название страны!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @NotBlank(message = "Заполните описание страны!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
