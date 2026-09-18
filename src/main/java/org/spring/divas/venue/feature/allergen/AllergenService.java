package org.spring.divas.venue.feature.allergen;

import java.util.List;

public interface AllergenService {
    AllergenResponseDto create(AllergenRequestDto dto);
    List<AllergenResponseDto> getAll();
    AllergenResponseDto getById(Long id);
    void delete(Long id);
    AllergenResponseDto update(Long id, AllergenRequestDto dto);
}
