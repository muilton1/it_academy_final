package it.academy.user_service.service;

import it.academy.user_service.dao.api.IRoleDao;
import it.academy.user_service.dao.api.IUserDao;
import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dao.enums.EUserRole;
import it.academy.user_service.dao.enums.EUserStatus;
import it.academy.user_service.dto.PageContent;
import it.academy.user_service.dto.UserDto;
import it.academy.user_service.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;


import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@Validated
public class UserService implements UserDetailsService, IUserService {
    @Autowired
    private final IUserDao userDao;
    @Autowired
    private final IRoleDao roleDao;

    private final PasswordEncoder encoder;

    public UserService(IUserDao userDao, IRoleDao roleDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public User create(@Valid UserDto userDto) {
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
        user.setRoles(Collections.singleton(this.roleDao.findById(1)));

        return this.userDao.save(user);
    }

    @Override
    public PageContent<User> getAll(Integer pageNo, Integer pageSize) {
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<User> page = this.userDao.findAll(paging);
        return new PageContent(page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                (int) page.getTotalElements(),
                page.isFirst(),
                page.getNumberOfElements(),
                page.isLast(),
                page.getContent());
    }
    @Transactional
    @Override
    public User update(UserDto userDto, UUID uuid, LocalDateTime lastKnowDtUpdate) {

        if (this.userDao.findByUuid(uuid) == null) {
            throw new IllegalArgumentException("Введен неверный UUID!");
        }

        User user = this.userDao.findByUuid(uuid);

        if (!user.getDtUpdate().equals(lastKnowDtUpdate)) {
            throw new IllegalArgumentException("Данные уже были кем-то изменены до вас!");
        }

        return this.userDao.save(mapUpdate(user, userDto));
    }

    @Override
    public User getOne(UUID uuid) {

        if (this.userDao.findByUuid(uuid) != null) {
            return this.userDao.findByUuid(uuid);

        } else throw new IllegalArgumentException("Введен неверный UUID!");
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        if (userDao.findByMail(mail) != null) {
            return userDao.findByMail(mail);

        } else throw new UsernameNotFoundException("Нет пользователя с такой почтой!");
    }

    public User mapUpdate(User user, UserDto userDto) {
        if (userDto.getMail() != null) {
            user.setMail(userDto.getMail());
        }
        if (userDto.getNick() != null) {
            user.setNick(userDto.getNick());
        }
        if (userDto.getStatus() != null) {
            user.setStatus(userDto.getStatus());
        }
        if (userDto.getRole() != null) {
            user.setRole(userDto.getRole());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        return user;
    }

}
