package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.allergen.AllergenRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IngredientRequestMapper {
    private final AllergenRepository allergenRepository;

    public Ingredient toEntity(IngredientRequestDto dto) {
        Set<Long> allergenIds = dto.allergens() != null ? dto.allergens() : new HashSet<>();
        return Ingredient.builder()
                .name(dto.name())
                .quantity(dto.quantity())
                .allergens(allergenIds.stream().map(
                        id -> allergenRepository.findById(id).orElseThrow()
                ).collect(Collectors.toSet()))
                .build();
    }
}
