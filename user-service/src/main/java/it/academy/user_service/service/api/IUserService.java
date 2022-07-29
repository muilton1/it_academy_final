package it.academy.user_service.service.api;

import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dto.PageContent;
import it.academy.user_service.dto.UserDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    void create(@Valid UserDto userDto);

    PageContent getAll(Integer pageNo, Integer pageSize);

    void update(UserDto userDto, UUID uuid, LocalDateTime lastKnowDtUpdate);

    User getOne(UUID uuid);
}
