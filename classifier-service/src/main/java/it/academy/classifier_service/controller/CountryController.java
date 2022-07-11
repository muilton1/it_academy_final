package it.academy.classifier_service.controller;


import it.academy.classifier_service.dao.entity.Country;
import it.academy.classifier_service.dto.CountryDto;
import it.academy.classifier_service.dto.PageContent;
import it.academy.classifier_service.service.api.ICountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.TimeZone;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/classifier/country")
@Validated
public class CountryController {
    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    private final ICountryService countryService;

    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping()
    public ResponseEntity<Country> create(@Valid @RequestBody CountryDto dto) {
        return new ResponseEntity<>(this.countryService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<PageContent<Country>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") @Min(1) Integer pageSize) {
        return new ResponseEntity<>(this.countryService.getAll(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UUID> readOne(@PathVariable UUID uuid) {
        return new ResponseEntity<>((this.countryService.getUuid(uuid)), HttpStatus.OK);
    }
}
