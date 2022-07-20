package it.academy.user_service.config;

import it.academy.user_service.dao.api.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class JpaUsersStorageConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private IUserDao authUserDetailsRepository;

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource, PasswordEncoder encoder) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        /*try {

            UserDetails admin = User.builder()
                    .username("admin")
                    .password(encoder.encode("321"))
                    .roles("ADMIN")
                    .build();

            manager.createUser(admin);
        } catch (DuplicateKeyException e) {
            //всё ок, уже есть
        }

         */

        return manager;
    }
}
