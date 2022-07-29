package it.academy.classifier_service.service;

import it.academy.classifier_service.dao.api.IConcertCategoryDao;
import it.academy.classifier_service.dao.entity.ConcertCategory;
import it.academy.classifier_service.dto.ConcertCategoryDto;
import it.academy.classifier_service.dto.PageContent;
import it.academy.classifier_service.mappers.FromConcertCategoryDtoToConcertCategory;
import it.academy.classifier_service.mappers.PageContentMapper;
import it.academy.classifier_service.service.api.IConcertCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ConcertCategoryService implements IConcertCategoryService {
    @Autowired
    private final IConcertCategoryDao categoryDao;
    private UserHolder holder;

    public ConcertCategoryService(IConcertCategoryDao categoryDao, UserHolder holder) {
        this.categoryDao = categoryDao;
        this.holder = holder;
    }

    @Transactional
    @Override
    public void create(@Valid ConcertCategoryDto dto) {
        FromConcertCategoryDtoToConcertCategory mapper = new FromConcertCategoryDtoToConcertCategory();
        this.categoryDao.save(mapper.convert(dto));
    }

    @Override
    public PageContent<ConcertCategoryDto> getAll(Integer pageNo, Integer pageSize) {
        PageContentMapper<ConcertCategoryDto> mapper = new PageContentMapper<>();
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<ConcertCategory> page = this.categoryDao.findAll(paging);
        Page<ConcertCategoryDto> dtoPage = page.map(ConcertCategoryDto::new);
        return mapper.map(dtoPage);
    }

    @Override
    public UUID getUuid(UUID uuid) {
        ConcertCategory byUuid = this.categoryDao.findByUuid(uuid);
        return byUuid.getUuid();
    }
}
