package it.academy.classifier_service.mappers;

import it.academy.classifier_service.dao.entity.Country;
import it.academy.classifier_service.dto.CountryDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class FromCountryDtoToCountry {
    public Country convert(CountryDto dto) {
        Country country = new Country();

        country.setUuid(UUID.randomUUID());
        country.setDtCreate(LocalDateTime.now());
        country.setDtUpdate(country.getDtCreate());
        country.setDescription(dto.getDescription());
        country.setTitle(dto.getTitle());

        return country;
    }
}
