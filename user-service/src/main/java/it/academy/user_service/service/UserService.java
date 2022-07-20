package it.academy.user_service.service;

import it.academy.user_service.controller.dto.InformationDto;
import it.academy.user_service.dao.api.IRoleDao;
import it.academy.user_service.dao.api.IUserDao;
import it.academy.user_service.dao.entity.Role;
import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dao.enums.EUserRole;
import it.academy.user_service.dao.enums.EUserStatus;
import it.academy.user_service.dto.PageContent;
import it.academy.user_service.dto.UserDto;
import it.academy.user_service.service.api.IUserService;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
@Transactional
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
        user.setMail(userDto.getMail());
        user.setNick(userDto.getNick());
        user.setStatus(userDto.getStatus());
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

    @Override
    public User update(UserDto userDto, UUID uuid, LocalDateTime lastKnowDtUpdate) {
        List<User> users = this.userDao.findAll();

        for (User user : users) {
            if (!user.getUuid().equals(uuid)) {
                throw new IllegalArgumentException("Введен некорректный UUID!");
            }
        }

        User user = this.userDao.findByUuid(uuid);

        if (!user.getDtUpdate().equals(lastKnowDtUpdate)) {
            throw new IllegalArgumentException("Данные уже были кем-то изменены до вас!");
        }

        user.setMail(userDto.getMail());
        user.setNick(userDto.getNick());
        user.setStatus(userDto.getStatus());
        user.setRole(userDto.getRole());
        user.setPassword(encoder.encode(user.getPassword()));

        this.userDao.save(user);

        return this.userDao.findByUuid(uuid);
    }

    @Override
    public User getOne(UUID uuid) {
        List<UUID> getUuid = userDao.findAll().stream().map(User::getUuid).collect(Collectors.toList());
        if (getUuid.contains(uuid)) {
            return this.userDao.findByUuid(uuid);
        } else throw new IllegalArgumentException("Введен неверный UUID!");
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userDao.findByMail(mail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
