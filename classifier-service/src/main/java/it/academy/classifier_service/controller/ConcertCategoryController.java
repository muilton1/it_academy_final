package it.academy.classifier_service.controller;

import it.academy.classifier_service.dto.ConcertCategoryDto;
import it.academy.classifier_service.dto.PageContent;
import it.academy.classifier_service.service.api.IConcertCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/classifier/concert/category")

public class ConcertCategoryController {
    private final IConcertCategoryService categoryService;

    public ConcertCategoryController(IConcertCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ConcertCategoryDto dto) {
        this.categoryService.create(dto);
    }

    @GetMapping()
    public ResponseEntity<PageContent<ConcertCategoryDto>> read(@RequestParam(value = "pageNo", defaultValue = "0") @Min(0) Integer pageNo,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") @Min(0) Integer pageSize) {
        return new ResponseEntity<>((this.categoryService.getAll(pageNo, pageSize)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UUID> readOne(@PathVariable UUID uuid) {
        return new ResponseEntity<>((this.categoryService.getUuid(uuid)), HttpStatus.OK);
    }

}
