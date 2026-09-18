package org.spring.divas.venue.feature.allergen;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/allergen")
public class AllergenController {
    private final AllergenService allergenService;

    @PostMapping
    public AllergenResponseDto create(@RequestBody AllergenRequestDto dto) {
        return allergenService.create(dto);
    }

    @GetMapping
    public List<AllergenResponseDto> getAll() {
        return allergenService.getAll();
    }

    @GetMapping("/{id}")
    public AllergenResponseDto getById(@PathVariable Long id) {
        return allergenService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        allergenService.delete(id);
    }

    @PutMapping("/{id}")
    public AllergenResponseDto update(
            @PathVariable Long id,
            @RequestBody AllergenRequestDto dto
    ) {
        return allergenService.update(id, dto);
    }

}
