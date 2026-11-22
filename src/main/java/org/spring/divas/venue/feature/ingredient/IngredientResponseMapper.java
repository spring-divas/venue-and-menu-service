package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.allergen.AllergenResponseMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IngredientResponseMapper {
    private final AllergenResponseMapper allergenResponseMapper;

    public IngredientResponseDto toDto(Ingredient ingredient) {
        return new IngredientResponseDto(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getAllergens() != null
                        ? ingredient.getAllergens().stream().map(allergenResponseMapper::toDto).collect(Collectors.toSet())
                        : Set.of()
        );
    }
}
