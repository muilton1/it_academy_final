package it.academy.events_service.service;


import it.academy.events_service.dao.api.IFilmDao;
import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import it.academy.events_service.dto.FilmDtoCreate;
import it.academy.events_service.dto.FilmDtoUpdate;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.dto.UserInformationDto;
import it.academy.events_service.mappers.FromFilmDtoCreateToFilmEvent;
import it.academy.events_service.mappers.FromFilmDtoUpdateToFilmEvent;
import it.academy.events_service.mappers.PageContentMapper;
import it.academy.events_service.service.api.IFilmService;
import it.academy.events_service.service.api.IRestTemplateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@Transactional(readOnly = true)
public class FilmService implements IFilmService {
    private RestTemplate template = new RestTemplate();
    private final IFilmDao filmDao;
    private UserHolder holder;
    private IRestTemplateService restTemplateService;

    public FilmService(IFilmDao filmDao, UserHolder holder, IRestTemplateService restTemplateService) {
        this.filmDao = filmDao;
        this.holder = holder;
        this.restTemplateService = restTemplateService;
    }

    @Transactional
    @Override
    public void create(@Valid FilmDtoCreate filmDto) {
        FromFilmDtoCreateToFilmEvent mapper = new FromFilmDtoCreateToFilmEvent(holder);
        restTemplateService.checkCountryUUID(filmDto.getCountry(), template);
        this.filmDao.save(mapper.convert(filmDto));
    }

    @Override
    public PageContent<FilmDtoCreate> getAll(Integer pageNo, Integer pageSize) {
        PageContentMapper<FilmDtoCreate> mapper = new PageContentMapper<>();
        PageContent<FilmDtoCreate> content;
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<FilmEvent> page = null;
        UserInformationDto user = holder.getUser();
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
            if (authorities.get(0).equals(new SimpleGrantedAuthority("ADMIN"))) {
                page = this.filmDao.findAll(paging);
            } else if (authorities.get(0).equals(new SimpleGrantedAuthority("USER"))) {
                page = this.filmDao.findAllByStatusAndCreator(EEventStatus.PUBLISHED, user.getUsername(), paging);
            }
        } else {
            page = this.filmDao.findAllByStatus(EEventStatus.PUBLISHED, paging);
        }
        Page<FilmDtoCreate> dtoPage = page.map(FilmDtoCreate::new);
        content = mapper.map(dtoPage);
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
    public void update(FilmDtoUpdate filmDtoUpdate, UUID uuid, LocalDateTime lastKnowDtUpdate) {
        FromFilmDtoUpdateToFilmEvent mapper = new FromFilmDtoUpdateToFilmEvent();
        if (this.filmDao.findByUuid(uuid) == null) {
            throw new IllegalArgumentException("Введен неверный UUID!");
        }
        FilmEvent filmEvent = this.filmDao.findByUuid(uuid);
        UserInformationDto user = holder.getUser();
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
            if (!filmEvent.getDtUpdate().equals(lastKnowDtUpdate)) {
                throw new IllegalArgumentException("Данные уже были кем-то изменены до вас!");
            }
            if (filmEvent.getCreator().equals(holder.getUser().getUsername()) || authorities.get(0).equals(new SimpleGrantedAuthority("ADMIN"))) {
                this.filmDao.save(mapper.convert(filmEvent, filmDtoUpdate));
            } else throw new IllegalArgumentException("Вы не можете редактировать чужой фильм!");
        }
    }
}
