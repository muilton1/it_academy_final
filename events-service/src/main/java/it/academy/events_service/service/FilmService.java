package it.academy.events_service.service;


import it.academy.events_service.dao.api.IFilmDao;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import it.academy.events_service.dao.enums.EEventType;
import it.academy.events_service.dto.*;
import it.academy.events_service.service.api.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class FilmService implements IFilmService {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private final IFilmDao filmDao;

    private UserHolder holder;

    public FilmService(IFilmDao filmDao, UserHolder holder) {
        this.filmDao = filmDao;
        this.holder = holder;
    }

    @Transactional
    @Override
    public FilmEvent create(@Valid FilmDtoCreate filmDto) {
        return this.filmDao.save(mapCreate(filmDto));
    }

    @Override
    public PageContent<FilmDtoCreate> getAll(Integer pageNo, Integer pageSize) {

        UserDetails details = holder.getUser();
        PageContent<FilmDtoCreate> content = new PageContent<>();

        if (Objects.isNull(details)) {
            PageRequest paging = PageRequest.of(pageNo, pageSize);
            Page<FilmEvent> page = this.filmDao.findAllByStatus(EEventStatus.PUBLISHED, paging);

            Page<FilmDtoCreate> dtoPage = page.map(FilmDtoCreate::new);
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
                Page<FilmEvent> page = this.filmDao.findAll(paging);

                Page<FilmDtoCreate> dtoPage = page.map(FilmDtoCreate::new);
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
                Page<FilmEvent> page = this.filmDao.findAllByStatusAndCreator(EEventStatus.PUBLISHED, holder.getUser().getUsername(), paging);

                Page<FilmDtoCreate> dtoPage = page.map(FilmDtoCreate::new);
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
    public FilmEvent getOne(UUID uuid) {

        if (this.filmDao.findByUuid(uuid) != null) {
            return this.filmDao.findByUuid(uuid);

        } else throw new IllegalArgumentException("Введен неверный UUID!");
    }

    @Transactional
    @Override
    public FilmEvent update(FilmDtoUpdate filmDtoUpdate, UUID uuid, LocalDateTime lastKnowDtUpdate) {

        if (this.filmDao.findByUuid(uuid) == null) {
            throw new IllegalArgumentException("Введен неверный UUID!");
        }

        FilmEvent filmEvent = this.filmDao.findByUuid(uuid);

        if (!filmEvent.getDtUpdate().equals(lastKnowDtUpdate)) {
            throw new IllegalArgumentException("Данные уже были кем-то изменены до вас!");
        }

        if (filmEvent.getCreator().equals(holder.getUser().getUsername())) {
            return this.filmDao.save(mapUpdate(filmEvent, filmDtoUpdate));
        } else throw new IllegalArgumentException("Вы не можете редактировать чужой фильм!");
    }

    public FilmEvent mapCreate(FilmDtoCreate filmDto) {

        FilmEvent filmEvent = new FilmEvent();

        filmEvent.setUuid(UUID.randomUUID());
        filmEvent.setDtCreate(LocalDateTime.now());
        filmEvent.setDtUpdate(filmEvent.getDtCreate());
        filmEvent.setTitle(filmDto.getTitle());
        filmEvent.setDescription(filmDto.getDescription());
        filmEvent.setDtEvent(filmDto.getDtEvent());
        filmEvent.setDtEndOfSale(filmDto.getDtEndOfSale());
        filmEvent.setType(EEventType.FILMS);
        filmEvent.setStatus(filmDto.getStatus());
        filmEvent.setReleaseDate(filmDto.getReleaseDate());
        filmEvent.setReleaseYear(filmDto.getReleaseYear());
        filmEvent.setDuration(filmDto.getDuration());

        if (filmDto.getCountry() != null) {
            filmEvent.setCountry(restTemplate.getForObject("http://localhost:8081/api/v1/classifier/country/" + filmDto.getCountry(), UUID.class));
        }

        filmEvent.setCreator(holder.getUser().getUsername());

        return filmEvent;
    }

    public FilmEvent mapUpdate(FilmEvent filmEvent, FilmDtoUpdate filmDtoUpdate) {

        if (filmDtoUpdate.getTitle() != null) {
            filmEvent.setTitle(filmDtoUpdate.getTitle());
        }

        if (filmDtoUpdate.getDescription() != null) {
            filmEvent.setTitle(filmDtoUpdate.getDescription());
        }

        if (filmDtoUpdate.getDtEvent() != null) {
            filmEvent.setDtEvent(filmDtoUpdate.getDtEvent());
        }
        if (filmDtoUpdate.getDtEndOfSale() != null) {
            filmEvent.setDtEndOfSale(filmDtoUpdate.getDtEndOfSale());
        }
        if (filmDtoUpdate.getStatus() != null) {
            filmEvent.setStatus(filmDtoUpdate.getStatus());
        }
        if (filmDtoUpdate.getReleaseDate() != null) {
            filmEvent.setReleaseDate(filmDtoUpdate.getReleaseDate());
        }

        if (filmDtoUpdate.getReleaseYear() != 0) {
            filmEvent.setReleaseYear(filmDtoUpdate.getReleaseYear());
        }

        if (filmDtoUpdate.getDuration() != 0) {
            filmEvent.setDuration(filmDtoUpdate.getDuration());
        }

        if (filmDtoUpdate.getCountry() != null) {
            filmEvent.setCountry(restTemplate.getForObject("http://localhost:8081/api/v1/classifier/country/" + filmDtoUpdate.getCountry(), UUID.class));
        }

        return filmEvent;
    }
}
