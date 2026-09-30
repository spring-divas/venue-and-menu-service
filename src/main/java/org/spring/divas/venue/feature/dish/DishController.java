package org.spring.divas.venue.feature.dish;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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

    @PostMapping("/batch")
    public List<DishResponseDto> getByIds(@RequestBody List<@NotNull Long> ids) {
        return dishService.getByIds(ids);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dishService.delete(id);
    }

    @PutMapping("/{id}")
    public DishResponseDto update(
            @PathVariable Long id,
            @RequestBody DishRequestDto dto
    ) {
        return dishService.update(id, dto);
    }

}
