package it.academy.user_service.mappers;

import it.academy.user_service.dao.api.IRoleDao;
import it.academy.user_service.dao.api.IUserDao;
import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dao.enums.EUserRole;
import it.academy.user_service.dao.enums.EUserStatus;
import it.academy.user_service.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;

public class FromUserDtoToUser {
    public User convert(UserDto userDto, IUserDao userDao, PasswordEncoder encoder, IRoleDao roleDao) {
        User user = new User();

        user.setUuid(UUID.randomUUID());
        user.setDtCreate(LocalDateTime.now());
        user.setDtUpdate(user.getDtCreate().truncatedTo(ChronoUnit.MILLIS));
        if (userDao.findByMail(userDto.getMail()) == null) {
            user.setMail(userDto.getMail());
        } else throw new IllegalArgumentException("Такой емейл уже зарегистрирован!");
        if (userDao.findByNick(userDto.getNick()) == null) {
            user.setNick(userDto.getNick());
        } else throw new IllegalArgumentException("Имя пользователя занято!");
        user.setStatus(EUserStatus.WAITING_ACTIVATION);
        user.setRole(EUserRole.USER);
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(Collections.singleton(roleDao.findById(1)));

        return user;
    }
}
