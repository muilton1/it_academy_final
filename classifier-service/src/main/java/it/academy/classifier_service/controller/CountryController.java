package it.academy.classifier_service.controller;


import it.academy.classifier_service.dto.CountryDto;
import it.academy.classifier_service.dto.PageContent;
import it.academy.classifier_service.service.api.ICountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/classifier/country")

public class CountryController {
    private final ICountryService countryService;

    public CountryController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CountryDto dto) {
        this.countryService.create(dto);
    }

    @GetMapping()
    public ResponseEntity<PageContent<CountryDto>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") @Min(1) Integer pageSize) {
        return new ResponseEntity<>(this.countryService.getAll(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UUID> readOne(@PathVariable UUID uuid) {
        return new ResponseEntity<>((this.countryService.getUuid(uuid)), HttpStatus.OK);
    }
}
