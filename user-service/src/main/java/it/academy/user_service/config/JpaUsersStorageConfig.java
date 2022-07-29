package it.academy.user_service.config;

import it.academy.user_service.dao.api.IRoleDao;
import it.academy.user_service.dao.api.IUserDao;
import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dao.enums.EUserRole;
import it.academy.user_service.dao.enums.EUserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;

@Configuration
public class JpaUsersStorageConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private IRoleDao roleDao;
    @Autowired
    private IUserDao userDao;

    @PostConstruct
    public void postConstruct() {

        if (userDao.findByRole(EUserRole.ADMIN) == null) {
            User admin = new User();

            PasswordEncoder encoder = new BCryptPasswordEncoder();

            admin.setUuid(UUID.randomUUID());
            admin.setDtCreate(LocalDateTime.now());
            admin.setDtUpdate(admin.getDtCreate().truncatedTo(ChronoUnit.MILLIS));
            admin.setRole(EUserRole.ADMIN);
            admin.setStatus(EUserStatus.ACTIVATED);
            admin.setNick("ADMIN");
            admin.setMail("ADMIN@ADMIN");
            admin.setRoles(Collections.singleton(this.roleDao.findById(2)));
            admin.setPassword(encoder.encode("123"));

            userDao.save(admin);
        }
    }
}
