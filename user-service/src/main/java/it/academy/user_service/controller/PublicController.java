package it.academy.user_service.controller;

import it.academy.user_service.controller.utils.JwtTokenUtil;
import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dto.InformationDto;
import it.academy.user_service.dto.LoginDto;
import it.academy.user_service.dto.UserDto;
import it.academy.user_service.service.UserHolder;
import it.academy.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

public class PublicController {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private UserHolder holder;

    public PublicController(UserService userService,
                            PasswordEncoder encoder, UserHolder holder) {
        this.userService = userService;
        this.encoder = encoder;
        this.holder = holder;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDto dto) {
        this.userService.create(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        UserDetails details = userService.loadUserByUsername(loginDto.getLogin());

        if (!encoder.matches(loginDto.getPassword(), details.getPassword())) {
            throw new IllegalArgumentException("Пароль неверный");
        }

        return JwtTokenUtil.generateAccessToken(details);
    }

    @GetMapping("/me")
    public InformationDto details() {
        User user = holder.getUser();
        return new InformationDto(user);
    }

    @GetMapping("/role")
    public String getAuthorities() {
        User user = holder.getUser();
        return user.getRole().name();
    }
}


