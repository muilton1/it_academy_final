package it.academy.classifier_service.service;

import it.academy.classifier_service.dao.api.IConcertCategoryDao;
import it.academy.classifier_service.dao.entity.ConcertCategory;
import it.academy.classifier_service.dto.ConcertCategoryDto;
import it.academy.classifier_service.dto.PageContent;
import it.academy.classifier_service.service.api.IConcertCategoryService;
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
public class ConcertCategoryService implements IConcertCategoryService {
    @Autowired
    private final IConcertCategoryDao categoryDao;

    public ConcertCategoryService(IConcertCategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public ConcertCategory create(@Valid ConcertCategoryDto dto) {
        ConcertCategory category = new ConcertCategory();

        category.setUuid(UUID.randomUUID());
        category.setDtCreate(LocalDateTime.now());
        category.setDtUpdate(category.getDtCreate());
        category.setTitle(dto.getTitle());

        return this.categoryDao.save(category);
    }

    @Override
    public PageContent<ConcertCategory> getAll(Integer pageNo, Integer pageSize) {
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<ConcertCategory> page = this.categoryDao.findAll(paging);
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
        ConcertCategory byUuid = this.categoryDao.findByUuid(uuid);
        return byUuid.getUuid();
    }
}
