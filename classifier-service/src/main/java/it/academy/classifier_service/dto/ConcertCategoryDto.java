package it.academy.classifier_service.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
@Validated
public class ConcertCategoryDto {
    private String title;

    public ConcertCategoryDto() {
    }
    @NotBlank(message = "Заполните название концерта!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
