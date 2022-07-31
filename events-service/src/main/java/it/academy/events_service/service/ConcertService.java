package it.academy.events_service.service;

import it.academy.events_service.dao.api.IConcertDao;
import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dao.enums.EEventStatus;
import it.academy.events_service.dto.ConcertDtoCreate;
import it.academy.events_service.dto.ConcertDtoUpdate;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.dto.UserInformationDto;
import it.academy.events_service.mappers.FromConcertDtoCreateToConcertEvent;
import it.academy.events_service.mappers.FromConcertDtoUpdateToConcertEvent;
import it.academy.events_service.mappers.PageContentMapper;
import it.academy.events_service.service.api.IConcertService;
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
public class ConcertService implements IConcertService {
    private RestTemplate template = new RestTemplate();
    private final IConcertDao concertDao;
    private UserHolder holder;
    private IRestTemplateService restTemplateService;

    public ConcertService(IConcertDao concertDao, UserHolder holder, IRestTemplateService restTemplateService) {
        this.concertDao = concertDao;
        this.holder = holder;
        this.restTemplateService = restTemplateService;
    }

    @Transactional
    @Override
    public void create(@Valid ConcertDtoCreate concertDto) {
        FromConcertDtoCreateToConcertEvent convert = new FromConcertDtoCreateToConcertEvent(holder);
        restTemplateService.checkCategoryUUID(concertDto.getCategory(), template);
        this.concertDao.save(convert.convert(concertDto));
    }

    @Override
    public PageContent<ConcertDtoCreate> getAll(Integer pageNo, Integer pageSize) {
        PageContentMapper<ConcertDtoCreate> mapper = new PageContentMapper<>();
        PageContent<ConcertDtoCreate> content;
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<ConcertEvent> page = null;
        UserInformationDto user = holder.getUser();
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
            if (authorities.get(0).equals(new SimpleGrantedAuthority("ADMIN"))) {
                page = this.concertDao.findAll(paging);
            } else if (authorities.get(0).equals(new SimpleGrantedAuthority("USER"))) {
                page = this.concertDao.findAllByStatusAndCreator(EEventStatus.PUBLISHED, user.getUsername(), paging);
            }
        } else {
            page = this.concertDao.findAllByStatus(EEventStatus.PUBLISHED, paging);
        }
        Page<ConcertDtoCreate> dtoPage = page.map(ConcertDtoCreate::new);
        content = mapper.map(dtoPage);
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
    public void update(ConcertDtoUpdate concertDtoUpdate, UUID uuid, LocalDateTime lastKnowDtUpdate) {
        FromConcertDtoUpdateToConcertEvent mapper = new FromConcertDtoUpdateToConcertEvent();
        if (this.concertDao.findByUuid(uuid) == null) {
            throw new IllegalArgumentException("Введен неверный UUID!");
        }
        ConcertEvent concertEvent = this.concertDao.findByUuid(uuid);
        UserInformationDto user = holder.getUser();
        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());
            if (!concertEvent.getDtUpdate().equals(lastKnowDtUpdate)) {
                throw new IllegalArgumentException("Данные уже были кем-то изменены до вас!");
            }
            if (concertEvent.getCreator().equals(user.getUsername()) || authorities.get(0).equals(new SimpleGrantedAuthority("ADMIN"))) {
                this.concertDao.save(mapper.convert(concertDtoUpdate, concertEvent));
            } else throw new IllegalArgumentException("Вы не можете редактировать чужой фильм!");
        }
    }
}
