package it.academy.user_service.service;

import it.academy.user_service.controller.dto.InformationDto;
import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
