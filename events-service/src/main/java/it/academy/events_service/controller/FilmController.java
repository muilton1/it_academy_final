package it.academy.events_service.controller;


import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dto.FilmDtoCreate;
import it.academy.events_service.dto.FilmDtoUpdate;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.service.api.IFilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/afisha/event/films")

public class FilmController {
    private final IFilmService filmService;

    public FilmController(IFilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody FilmDtoCreate filmDto) {
        this.filmService.create(filmDto);
    }

    @GetMapping()
    public ResponseEntity<PageContent<FilmDtoCreate>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                           @RequestParam(value = "pageSize", defaultValue = "20") @Min(1) Integer pageSize) {
        return new ResponseEntity<>((this.filmService.getAll(pageNo, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FilmEvent> readOne(@PathVariable UUID uuid) {
        return new ResponseEntity<>((this.filmService.getOne(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_create}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody FilmDtoUpdate filmDtoUpdate, @PathVariable UUID uuid, @PathVariable Long dt_create) {
        LocalDateTime lastKnowDtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_create), ZoneId.systemDefault());
        this.filmService.update(filmDtoUpdate, uuid, lastKnowDtUpdate);
    }

}
