package it.academy.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories("it.academy.user_service.dao.api")
public class UserApplication {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("ru"));
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(UserApplication.class, args);
    }
}
