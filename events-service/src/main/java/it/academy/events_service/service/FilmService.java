package it.academy.events_service.service;


import it.academy.events_service.dao.api.IFilmDao;

import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventType;
import it.academy.events_service.dto.FilmDto;
import it.academy.events_service.dto.FilmDtoUpdate;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.service.api.IFilmService;
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
public class FilmService implements IFilmService {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private final IFilmDao filmDao;

    public FilmService(IFilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @Override
    public FilmEvent create(@Valid FilmDto filmDto) {
        return this.filmDao.save(mapCreate(filmDto));
    }

    @Override
    public PageContent<FilmDto> getAll(Integer pageNo, Integer pageSize) {

        PageRequest paging = PageRequest.of(pageNo, pageSize);

        Page<FilmEvent> page = this.filmDao.findAll(paging);
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
    public FilmEvent getOne(UUID uuid) {

        List<UUID> getUuid = filmDao.findAll().stream().map(FilmEvent::getUuid).collect(Collectors.toList());

        if (getUuid.contains(uuid)) {
            return this.filmDao.findByUuid(uuid);

        } else throw new IllegalArgumentException("???????????? ???????????????? UUID!");
    }


    @Override
    public FilmEvent update(FilmDtoUpdate filmDtoUpdate, UUID uuid, LocalDateTime lastKnowDtUpdate) {

        List<UUID> getUuid = filmDao.findAll().stream().map(FilmEvent::getUuid).collect(Collectors.toList());

        if (!getUuid.contains(uuid)) {
            throw new IllegalArgumentException("???????????? ???????????????? UUID!");
        }

        FilmEvent filmEvent = this.filmDao.findByUuid(uuid);

        if (!filmEvent.getDtUpdate().equals(lastKnowDtUpdate)) {
            throw new IllegalArgumentException("???????????? ?????? ???????? ??????-???? ???????????????? ???? ??????!");
        }

        return this.filmDao.save(mapUpdate(filmEvent, filmDtoUpdate));
    }

    public FilmEvent mapCreate(FilmDto filmDto) {

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
