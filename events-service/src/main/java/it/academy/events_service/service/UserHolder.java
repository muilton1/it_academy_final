package it.academy.events_service.service;

import it.academy.events_service.dto.UserInformationDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class UserHolder {
    public UserInformationDto getUser() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == "anonymousUser") {
            return null;
        } else {
            return (UserInformationDto) user;
        }
    }
}

