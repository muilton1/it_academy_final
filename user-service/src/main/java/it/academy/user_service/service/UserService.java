package it.academy.user_service.service;

import it.academy.user_service.dao.api.IRoleDao;
import it.academy.user_service.dao.api.IUserDao;
import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dto.PageContent;
import it.academy.user_service.dto.UserDto;
import it.academy.user_service.mappers.FromUserDtoToUser;
import it.academy.user_service.mappers.FromUserDtoUpdateToUser;
import it.academy.user_service.mappers.PageContentMapper;
import it.academy.user_service.service.api.IUserService;
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
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@Validated
public class UserService implements UserDetailsService, IUserService {
    private final IUserDao userDao;
    private final IRoleDao roleDao;
    private final PasswordEncoder encoder;

    public UserService(IUserDao userDao, IRoleDao roleDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public void create(@Valid UserDto userDto) {
        FromUserDtoToUser mapper = new FromUserDtoToUser();
        this.userDao.save(mapper.convert(userDto, userDao, encoder, roleDao));
    }

    @Override
    public PageContent<User> getAll(Integer pageNo, Integer pageSize) {
        PageContentMapper<User> mapper = new PageContentMapper<>();
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        Page<User> page = this.userDao.findAll(paging);
        return mapper.map(page);
    }

    @Transactional
    @Override
    public void update(UserDto userDto, UUID uuid, LocalDateTime lastKnowDtUpdate) {
        FromUserDtoUpdateToUser mapper = new FromUserDtoUpdateToUser();
        if (this.userDao.findByUuid(uuid) == null) {
            throw new IllegalArgumentException("Введен неверный UUID!");
        }
        User user = this.userDao.findByUuid(uuid);
        if (!user.getDtUpdate().equals(lastKnowDtUpdate)) {
            throw new IllegalArgumentException("Данные уже были кем-то изменены до вас!");
        }
        this.userDao.save(mapper.convert(user, userDto, encoder));
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
}
