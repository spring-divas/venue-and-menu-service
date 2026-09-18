package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    @PostMapping
    public DishResponseDto create(@RequestBody DishRequestDto dto) {
        return dishService.create(dto);
    }

    @GetMapping
    public List<DishResponseDto> getAll() {
        return dishService.getAll();
    }

    @GetMapping("/{id}")
    public DishResponseDto getById(@PathVariable Long id) {
        return dishService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dishService.delete(id);
    }

    @PostMapping("/{id}")
    public DishResponseDto update(
            @PathVariable Long id,
            @RequestBody DishRequestDto dto
    ) {
        return dishService.update(id, dto);
    }

}
