package it.academy.classifier_service.service.api;

import it.academy.classifier_service.dto.ConcertCategoryDto;
import it.academy.classifier_service.dto.PageContent;

import javax.validation.Valid;
import java.util.UUID;

public interface IConcertCategoryService {
    void create(@Valid ConcertCategoryDto dto);

    PageContent getAll(Integer pageNo, Integer pageSize);

    UUID getUuid(UUID uuid);
}
