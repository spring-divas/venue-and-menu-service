package org.spring.divas.venue.feature.allergen;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/allergen")
public class AllergenController {
    private final AllergenService allergenService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AllergenResponseDto create(@Valid @RequestBody AllergenRequestDto dto) {
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
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        allergenService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AllergenResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody AllergenRequestDto dto
    ) {
        return allergenService.update(id, dto);
    }

}
