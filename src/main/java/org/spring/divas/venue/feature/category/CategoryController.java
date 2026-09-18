package org.spring.divas.venue.feature.category;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return categoryService.create(dto);
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @RequestBody CategoryRequestDto dto
    ) {
        return categoryService.update(id, dto);
    }

}
