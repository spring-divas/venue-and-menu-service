package org.spring.divas.venue.feature.dish;

import java.util.List;

public interface DishService {
    DishResponseDto create(DishRequestDto dto);
    List<DishResponseDto> getAll();
    DishResponseDto getById(Long id);
    void delete(Long id);
    DishResponseDto update(Long id, DishRequestDto dto);
}
