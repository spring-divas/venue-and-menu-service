package org.spring.divas.venue.feature.ingredient;

import org.spring.divas.venue.feature.allergen.AllergenResponseDto;

import java.util.Set;

public record IngredientResponseDto(
        Long id,
        String name,
        Integer quantity,
        Set<AllergenResponseDto> allergens
) {}
