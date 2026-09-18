package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping
    public IngredientResponseDto create(@RequestBody IngredientRequestDto dto) {
        return ingredientService.create(dto);
    }

    @GetMapping
    public List<IngredientResponseDto> getAll() {
        return ingredientService.getAll();
    }

    @GetMapping("/{id}")
    public IngredientResponseDto getById(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ingredientService.delete(id);
    }

    @PostMapping("/{id}")
    public IngredientResponseDto update(
            @PathVariable Long id,
            @RequestBody IngredientRequestDto dto
    ) {
        return ingredientService.update(id, dto);
    }

}
