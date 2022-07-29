package it.academy.classifier_service.mappers;

import it.academy.classifier_service.dao.entity.ConcertCategory;
import it.academy.classifier_service.dto.ConcertCategoryDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class FromConcertCategoryDtoToConcertCategory {
    public ConcertCategory convert(ConcertCategoryDto dto) {
        ConcertCategory category = new ConcertCategory();

        category.setUuid(UUID.randomUUID());
        category.setDtCreate(LocalDateTime.now());
        category.setDtUpdate(category.getDtCreate());
        category.setTitle(dto.getTitle());

        return category;
    }
}
