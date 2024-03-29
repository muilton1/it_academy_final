package it.academy.classifier_service.service.api;

import it.academy.classifier_service.dto.CountryDto;
import it.academy.classifier_service.dto.PageContent;

import javax.validation.Valid;
import java.util.UUID;

public interface ICountryService {

    void create(@Valid CountryDto dto);

    PageContent getAll(Integer pageNo, Integer pageSize);

    UUID getUuid(UUID uuid);
}
