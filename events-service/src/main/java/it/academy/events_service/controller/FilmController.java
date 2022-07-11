package it.academy.events_service.controller;


import it.academy.events_service.dao.entity.FilmEvent;
import it.academy.events_service.dto.FilmDto;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.service.api.IFilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/afisha/event/films")
@Validated
public class FilmController {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    private final IFilmService filmService;

    public FilmController(IFilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping()
    public ResponseEntity<FilmEvent> create(@Valid @RequestBody FilmDto filmDto) {
        return new ResponseEntity<>(this.filmService.create(filmDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<PageContent<FilmEvent>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = "20") @Min(1) Integer pageSize) {
        return new ResponseEntity<>((this.filmService.getAll(pageNo, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FilmEvent> readOne(@PathVariable UUID uuid) {
        return new ResponseEntity<>((this.filmService.getOne(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_create}")
    public ResponseEntity<FilmEvent> update(@Valid @RequestBody FilmDto filmDto, @PathVariable UUID uuid, @PathVariable Long dt_create) {
        LocalDateTime lastKnowDtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_create), ZoneId.systemDefault());
        return new ResponseEntity<>(this.filmService.update(filmDto, uuid, lastKnowDtUpdate), HttpStatus.OK);
    }

}
