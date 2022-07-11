package it.academy.events_service.service;

import it.academy.events_service.dao.api.IConcertDao;

import it.academy.events_service.dao.entity.ConcertEvent;

import it.academy.events_service.dao.enums.EEventType;
import it.academy.events_service.dto.ConcertDto;

import it.academy.events_service.dto.PageContent;
import it.academy.events_service.service.api.IConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Validated
public class ConcertService implements IConcertService {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private final IConcertDao concertDao;

    public ConcertService(IConcertDao concertDao) {
        this.concertDao = concertDao;
    }

    @Override
    public ConcertEvent create(@Valid ConcertDto concertDto) {

        ConcertEvent concertEvent = new ConcertEvent();

        concertEvent.setUuid(UUID.randomUUID());
        concertEvent.setDtCreate(LocalDateTime.now());
        concertEvent.setDtUpdate(concertEvent.getDtCreate());
        concertEvent.setTitle(concertDto.getTitle());
        concertEvent.setDescription(concertDto.getDescription());
        concertEvent.setDtEvent(concertDto.getDtEvent());
        concertEvent.setDtEndOfSale(concertDto.getDtEndOfSale());
        concertEvent.setType(EEventType.CONCERTS);
        concertEvent.setStatus(concertDto.getStatus());

        if (concertDto.getCategory() != null) {
            concertEvent.setCategory(restTemplate.getForObject("http://localhost:8081/api/v1/classifier/concert/category/" + concertDto.getCategory(), UUID.class));
        }

        return this.concertDao.save(concertEvent);
    }

    @Override
    public PageContent<ConcertEvent> getAll(Integer pageNo, Integer pageSize) {
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<ConcertEvent> page = this.concertDao.findAll(paging);
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
    public ConcertEvent getOne(UUID uuid) {
        List<UUID> getUuid = concertDao.findAll().stream().map(ConcertEvent::getUuid).collect(Collectors.toList());
        if (getUuid.contains(uuid)) {
            return this.concertDao.findByUuid(uuid);
        } else throw new IllegalArgumentException("Введен неверный UUID!");
    }


    @Override
    public ConcertEvent update( ConcertDto concertDto, UUID uuid, LocalDateTime lastKnowDtUpdate) {

        ConcertEvent concertEvent = this.concertDao.findByUuid(uuid);

        concertEvent.setTitle(concertDto.getTitle());
        concertEvent.setDescription(concertDto.getDescription());
        concertEvent.setDtEvent(concertDto.getDtEvent());
        concertEvent.setDtEndOfSale(concertDto.getDtEndOfSale());
        concertEvent.setStatus(concertDto.getStatus());

        if (concertDto.getCategory() != null) {
            concertEvent.setCategory(restTemplate.getForObject("http://localhost:8081/api/v1/classifier/concert/category/" + concertDto.getCategory(), UUID.class));
        }

        this.concertDao.save(concertEvent);

        return this.concertDao.findByUuid(uuid);
    }

}
