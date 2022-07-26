package it.academy.user_service.controller;

import it.academy.user_service.controller.dto.InformationDto;
import it.academy.user_service.controller.dto.LoginDto;
import it.academy.user_service.controller.dto.RegistrationDto;
import it.academy.user_service.controller.utils.JwtTokenUtil;

import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dao.enums.EUserRole;
import it.academy.user_service.dto.UserDto;
import it.academy.user_service.service.UserHolder;
import it.academy.user_service.service.UserService;
import it.academy.user_service.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class PublicController {
    @Autowired
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
    public ResponseEntity<InformationDto> create(@Valid @RequestBody UserDto dto) {
        return new ResponseEntity<>(new InformationDto(this.userService.create(dto)), HttpStatus.CREATED);
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


