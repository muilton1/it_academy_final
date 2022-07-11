package it.academy.classifier_service.service;

import it.academy.classifier_service.dao.api.ICountryDao;
import it.academy.classifier_service.dao.entity.Country;
import it.academy.classifier_service.dto.CountryDto;
import it.academy.classifier_service.dto.PageContent;
import it.academy.classifier_service.service.api.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Validated
public class CountryService implements ICountryService {
    @Autowired
    private final ICountryDao countryDao;


    public CountryService(ICountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public Country create(@Valid CountryDto dto) {
        Country country = new Country();

        country.setUuid(UUID.randomUUID());
        country.setDtCreate(LocalDateTime.now());
        country.setDtUpdate(country.getDtCreate());
        country.setDescription(dto.getDescription());
        country.setTitle(dto.getTitle());

        return this.countryDao.save(country);
    }

    @Override
    public PageContent<Country> getAll(Integer pageNo, Integer pageSize) {
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<Country> page = this.countryDao.findAll(paging);
        return new PageContent(page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                (int) page.getTotalElements(),
                page.isFirst(),
                page.getNumberOfElements(),
                page.isLast(),
                page.getContent());
    }

    @Override
    public UUID getUuid(UUID uuid) {
        Country byUuid = this.countryDao.findByUuid(uuid);
        return byUuid.getUuid();
    }
}
