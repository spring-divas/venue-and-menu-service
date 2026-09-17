package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.allergen.AllergenResponseMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IngredientResponseMapper {
    private AllergenResponseMapper allergenResponseMapper;

    public IngredientResponseDto toDto(Ingredient ingredient) {
        return IngredientResponseDto.builder()
                .name(ingredient.getName())
                .quantity(ingredient.getQuantity())
                .allergens(ingredient.getAllergens().stream().map(
                        allergenResponseMapper::toDto
                ).collect(Collectors.toSet()))
                .build();
    }
}
