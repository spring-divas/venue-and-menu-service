package org.spring.divas.venue.feature.dish;

import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.ingredient.IngredientResponseDto;

import java.math.BigDecimal;
import java.util.Set;

public record DishResponseDto(
        Long id,
        Long venueId,
        CategoryResponseDto category,
        String name,
        String description,
        BigDecimal price,
        Float rating,
        Integer reviewCount,
        Set<IngredientResponseDto> ingredients
) {}
