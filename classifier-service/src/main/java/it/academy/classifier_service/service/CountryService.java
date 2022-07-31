package it.academy.classifier_service.service;


import it.academy.classifier_service.dao.api.ICountryDao;
import it.academy.classifier_service.dao.entity.Country;
import it.academy.classifier_service.dto.CountryDto;
import it.academy.classifier_service.dto.PageContent;
import it.academy.classifier_service.mappers.FromCountryDtoToCountry;
import it.academy.classifier_service.mappers.PageContentMapper;
import it.academy.classifier_service.service.api.ICountryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
@Transactional(readOnly = true)
public class CountryService implements ICountryService {
    private final ICountryDao countryDao;

    public CountryService(ICountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Transactional
    @Override
    public void create(@Valid CountryDto dto) {
        FromCountryDtoToCountry mapper = new FromCountryDtoToCountry();
        this.countryDao.save(mapper.convert(dto));
    }

    @Override
    public PageContent<CountryDto> getAll(Integer pageNo, Integer pageSize) {
        PageContentMapper<CountryDto> mapper = new PageContentMapper<>();
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<Country> page = this.countryDao.findAll(paging);
        Page<CountryDto> dtoPage = page.map(CountryDto::new);
        return mapper.map(dtoPage);
    }

    @Override
    public UUID getUuid(UUID uuid) {
        Country byUuid = this.countryDao.findByUuid(uuid);
        return byUuid.getUuid();
    }
}
