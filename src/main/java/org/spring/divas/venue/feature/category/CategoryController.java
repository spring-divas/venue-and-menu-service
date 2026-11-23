package org.spring.divas.venue.feature.category;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_SENIOR ' + #p0.venueId)")
    @ResponseStatus(HttpStatus.CREATED)
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
    @PreAuthorize("hasRole('ADMIN') or hasAuthority('ROLE_SENIOR ' + @categoryServiceImpl.getById(#p0).venueId)")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') "
            + "or (hasAuthority('ROLE_SENIOR ' + @categoryServiceImpl.getById(#p0).venueId) "
            + "and hasAuthority('ROLE_SENIOR ' + #p1.venueId))")
    public CategoryResponseDto update(
            @PathVariable Long id,
            @RequestBody CategoryRequestDto dto
    ) {
        return categoryService.update(id, dto);
    }

}
