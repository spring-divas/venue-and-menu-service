package org.spring.divas.venue.feature.ingredient;

import org.springframework.stereotype.Component;

@Component
public class IngredientResponseMapper {
    public IngredientResponseDto toDto(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .name(ingredient.getName())
                .quantity(ingredient.getQuantity())
                .allergens(ingredient.getAllergens())
                .build();
    }
}
