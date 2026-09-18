package org.spring.divas.venue.feature.category;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto create(CategoryRequestDto dto);
    List<CategoryResponseDto> getAll();
    CategoryResponseDto getById(Long id);
    void delete(Long id);
    CategoryResponseDto update(Long id, CategoryRequestDto dto);
}
