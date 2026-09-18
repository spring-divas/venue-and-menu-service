package org.spring.divas.venue.feature.ingredient;

import java.util.List;

public interface IngredientService {
    IngredientResponseDto create(IngredientRequestDto dto);
    List<IngredientResponseDto> getAll();
    IngredientResponseDto getById(Long id);
    void delete(Long id);
    IngredientResponseDto update(Long id, IngredientRequestDto dto);
}
