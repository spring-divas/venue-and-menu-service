package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.allergen.AllergenRequestMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IngredientRequestMapper {
    private final AllergenRequestMapper allergenRequestMapper;

    public Ingredient toEntity(IngredientRequestDto dto) {
        return Ingredient.builder()
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .allergens(dto.getAllergens().stream().map(
                        allergenRequestMapper::toEntity
                ).collect(Collectors.toSet()))
                .build();
    }
}
