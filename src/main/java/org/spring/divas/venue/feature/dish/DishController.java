package org.spring.divas.venue.feature.dish;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @PostMapping
    public DishResponseDto create(@RequestBody DishRequestDto dto) {
        return null;
    }

    @GetMapping
    public List<DishResponseDto> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public DishResponseDto getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public DishResponseDto delete(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}")
    public DishResponseDto update(
            @PathVariable Long id,
            @RequestBody DishRequestDto dto
    ) {
        return null;
    }

}
