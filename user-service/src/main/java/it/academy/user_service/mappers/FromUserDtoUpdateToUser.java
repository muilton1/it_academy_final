package it.academy.user_service.mappers;

import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public class FromUserDtoUpdateToUser {
    public User convert(User user, UserDto userDto, PasswordEncoder encoder) {
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
