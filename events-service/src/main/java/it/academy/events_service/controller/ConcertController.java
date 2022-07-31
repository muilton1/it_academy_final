package it.academy.events_service.controller;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dto.ConcertDtoCreate;
import it.academy.events_service.dto.ConcertDtoUpdate;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.service.api.IConcertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequestMapping("/afisha/event/concerts")

public class ConcertController {
    private final IConcertService concertService;

    public ConcertController(IConcertService concertService) {
        this.concertService = concertService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ConcertDtoCreate concertDto) {
        this.concertService.create(concertDto);
    }

    @GetMapping()
    public ResponseEntity<PageContent<ConcertDtoCreate>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                              @RequestParam(value = "pageSize", defaultValue = "20") @Min(1) Integer pageSize) {
        return new ResponseEntity<>((this.concertService.getAll(pageNo, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ConcertEvent> readOne(@PathVariable UUID uuid) {
        return new ResponseEntity<>((this.concertService.getOne(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_create}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody ConcertDtoUpdate concertDto, @PathVariable UUID
            uuid, @PathVariable Long dt_create) {
        LocalDateTime lastKnowDtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_create), ZoneId.systemDefault());
        this.concertService.update(concertDto, uuid, lastKnowDtUpdate);
    }

}
