package it.academy.events_service.service;

import it.academy.events_service.dao.api.IConcertDao;

import it.academy.events_service.dao.entity.ConcertEvent;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import it.academy.events_service.dao.enums.EEventType;
import it.academy.events_service.dto.ConcertDtoCreate;

import it.academy.events_service.dto.ConcertDtoUpdate;
import it.academy.events_service.dto.FilmDtoCreate;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.service.api.IConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Validated
@Transactional(readOnly = true)
public class ConcertService implements IConcertService {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private final IConcertDao concertDao;

    private UserHolder holder;

    public ConcertService(IConcertDao concertDao, UserHolder holder) {
        this.concertDao = concertDao;
        this.holder = holder;

    }

    @Transactional
    @Override
    public ConcertEvent create(@Valid ConcertDtoCreate concertDto) {
        return this.concertDao.save(mapCreate(concertDto));
    }

    @Override
    public PageContent<ConcertDtoCreate> getAll(Integer pageNo, Integer pageSize) {

        PageContent<ConcertDtoCreate> content = new PageContent<>();

        if (Objects.isNull(holder.getUser())) {
            PageRequest paging = PageRequest.of(pageNo, pageSize);
            Page<ConcertEvent> page = this.concertDao.findAllByStatus(EEventStatus.PUBLISHED, paging);

            Page<ConcertDtoCreate> dtoPage = page.map(ConcertDtoCreate::new);
            content = new PageContent<>(dtoPage.getNumber(),
                    dtoPage.getSize(),
                    dtoPage.getTotalPages(),
                    (int) dtoPage.getTotalElements(),
                    dtoPage.isFirst(),
                    dtoPage.getNumberOfElements(),
                    dtoPage.isLast(),
                    dtoPage.getContent());

        } else {

            Collection<? extends GrantedAuthority> authorities = holder.getUser().getAuthorities();
            List<GrantedAuthority> authorities1 = new ArrayList<>(authorities);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authorities1.get(0).toString().replace("\"", ""));

            if (authority.equals(new SimpleGrantedAuthority("ADMIN"))) {

                PageRequest paging = PageRequest.of(pageNo, pageSize);
                Page<ConcertEvent> page = this.concertDao.findAll(paging);

                Page<ConcertDtoCreate> dtoPage = page.map(ConcertDtoCreate::new);
                content = new PageContent<>(dtoPage.getNumber(),
                        dtoPage.getSize(),
                        dtoPage.getTotalPages(),
                        (int) dtoPage.getTotalElements(),
                        dtoPage.isFirst(),
                        dtoPage.getNumberOfElements(),
                        dtoPage.isLast(),
                        dtoPage.getContent());

            }

            if (authority.equals(new SimpleGrantedAuthority("USER"))) {

                PageRequest paging = PageRequest.of(pageNo, pageSize);
                Page<ConcertEvent> page = this.concertDao.findAllByStatusAndCreator(EEventStatus.PUBLISHED, holder.getUser().getUsername(), paging);

                Page<ConcertDtoCreate> dtoPage = page.map(ConcertDtoCreate::new);
                content = new PageContent<>(dtoPage.getNumber(),
                        dtoPage.getSize(),
                        dtoPage.getTotalPages(),
                        (int) dtoPage.getTotalElements(),
                        dtoPage.isFirst(),
                        dtoPage.getNumberOfElements(),
                        dtoPage.isLast(),
                        dtoPage.getContent());

            }
        }
        return content;
    }

    @Override
    public ConcertEvent getOne(UUID uuid) {

        if (this.concertDao.findByUuid(uuid) != null) {
            return this.concertDao.findByUuid(uuid);

        } else throw new IllegalArgumentException("Введен неверный UUID!");
    }

    @Transactional
    @Override
    public ConcertEvent update(ConcertDtoUpdate concertDtoUpdate, UUID uuid, LocalDateTime lastKnowDtUpdate) {

        if (this.concertDao.findByUuid(uuid) == null) {
            throw new IllegalArgumentException("Введен неверный UUID!");
        }

        ConcertEvent concertEvent = this.concertDao.findByUuid(uuid);

        if (!concertEvent.getDtUpdate().equals(lastKnowDtUpdate)) {
            throw new IllegalArgumentException("Данные уже были кем-то изменены до вас!");
        }

        return this.concertDao.save(mapUpdate(concertEvent, concertDtoUpdate));
    }

    public ConcertEvent mapCreate(ConcertDtoCreate concertDto) {
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

        return concertEvent;
    }

    public ConcertEvent mapUpdate(ConcertEvent concertEvent, ConcertDtoUpdate concertDtoUpdate) {

        if (concertDtoUpdate.getTitle() != null) {
            concertEvent.setTitle(concertDtoUpdate.getTitle());
        }

        if (concertDtoUpdate.getDescription() != null) {
            concertEvent.setTitle(concertDtoUpdate.getDescription());
        }

        if (concertDtoUpdate.getDtEvent() != null) {
            concertEvent.setDtEvent(concertDtoUpdate.getDtEvent());
        }
        if (concertDtoUpdate.getDtEndOfSale() != null) {
            concertEvent.setDtEndOfSale(concertDtoUpdate.getDtEndOfSale());
        }
        if (concertDtoUpdate.getStatus() != null) {
            concertEvent.setStatus(concertDtoUpdate.getStatus());
        }

        if (concertDtoUpdate.getCategory() != null) {
            concertEvent.setCategory(restTemplate.getForObject("http://localhost:8081/api/v1/classifier/concert/category/" + concertDtoUpdate.getCategory(), UUID.class));
        }

        return concertEvent;
    }

}
