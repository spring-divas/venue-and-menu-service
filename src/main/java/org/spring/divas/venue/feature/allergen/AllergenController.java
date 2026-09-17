package org.spring.divas.venue.feature.allergen;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/allergen")
public class AllergenController {

    @PostMapping
    public AllergenResponseDto create(@RequestBody AllergenRequestDto dto) {
        return null;
    }

    @GetMapping
    public List<AllergenResponseDto> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public AllergenResponseDto getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public AllergenRequestDto delete(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}")
    public AllergenResponseDto update(
            @PathVariable Long id,
            @RequestBody AllergenRequestDto dto
    ) {
        return null;
    }

}
