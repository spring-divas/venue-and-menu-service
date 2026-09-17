package org.spring.divas.venue.feature.ingredient;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @PostMapping
    public IngredientResponseDto create(@RequestBody IngredientRequestDto dto) {
        return null;
    }

    @GetMapping
    public List<IngredientResponseDto> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public IngredientResponseDto getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public IngredientResponseDto delete(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}")
    public IngredientResponseDto update(
            @PathVariable Long id,
            @RequestBody IngredientRequestDto dto
    ) {
        return null;
    }

}
