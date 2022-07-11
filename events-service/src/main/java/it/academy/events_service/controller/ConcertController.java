package it.academy.events_service.controller;

import it.academy.events_service.dao.entity.ConcertEvent;
import it.academy.events_service.dto.ConcertDto;
import it.academy.events_service.dto.PageContent;
import it.academy.events_service.service.api.IConcertService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/afisha/event/concerts")
@Validated
public class ConcertController {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    @Autowired
    private final IConcertService concertService;

    public ConcertController(IConcertService concertService) {
        this.concertService = concertService;
    }

    @PostMapping()
    public ResponseEntity<ConcertEvent> create(@Valid @RequestBody ConcertDto concertDto) {
        return new ResponseEntity<>(this.concertService.create(concertDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<PageContent<ConcertEvent>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "20") @Min(1) Integer pageSize) {
        return new ResponseEntity<>((this.concertService.getAll(pageNo, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ConcertEvent> readOne(@Valid @PathVariable UUID uuid) {
        return new ResponseEntity<>((this.concertService.getOne(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_create}")
    public ResponseEntity<ConcertEvent> update(@Valid @RequestBody ConcertDto concertDto, @PathVariable UUID
            uuid, @PathVariable Long dt_create) {
        LocalDateTime lastKnowDtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dt_create), ZoneId.systemDefault());
        return new ResponseEntity<>(this.concertService.update(concertDto, uuid, lastKnowDtUpdate), HttpStatus.OK);
    }

}
