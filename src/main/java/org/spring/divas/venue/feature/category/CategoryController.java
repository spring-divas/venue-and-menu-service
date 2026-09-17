package org.spring.divas.venue.feature.category;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return null;
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public CategoryResponseDto delete(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @RequestBody CategoryRequestDto dto
    ) {
        return null;
    }

}
