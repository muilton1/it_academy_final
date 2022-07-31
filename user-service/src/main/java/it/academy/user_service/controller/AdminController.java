package it.academy.user_service.controller;

import it.academy.user_service.dao.entity.User;
import it.academy.user_service.dto.PageContent;
import it.academy.user_service.dto.UserDto;
import it.academy.user_service.service.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/users")

public class AdminController {
    private final IUserService userService;

    public AdminController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDto userDto) {
        this.userService.create(userDto);
    }

    @GetMapping()
    public ResponseEntity<PageContent<User>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                  @RequestParam(value = "pageSize", defaultValue = "20") @Min(1) Integer pageSize) {
        return new ResponseEntity<>((this.userService.getAll(pageNo, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> readOne(@PathVariable UUID uuid) {
        return new ResponseEntity<>((this.userService.getOne(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_create}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody UserDto userDto, @PathVariable UUID uuid, @PathVariable Long dt_create) {
        LocalDateTime lastKnowDtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_create), ZoneId.systemDefault());
        this.userService.update(userDto, uuid, lastKnowDtUpdate);
    }
}
