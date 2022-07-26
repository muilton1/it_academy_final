package it.academy.events_service.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class UserHolder {

    public UserDetails getUser() {

        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == "anonymousUser") {
            return null;

        } else {
            return (UserDetails) user;
        }
    }
}

