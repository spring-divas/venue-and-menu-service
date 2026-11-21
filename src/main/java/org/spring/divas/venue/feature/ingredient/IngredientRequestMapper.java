package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.allergen.Allergen;
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
        Set<Allergen> allergens = dto.getAllergens() == null
                ? new HashSet<>()
                : dto.getAllergens().stream()
                  .map(id -> allergenRepository.findById(id).orElseThrow())
                  .collect(Collectors.toSet());
        return Ingredient.builder()
                .name(dto.getName())
                .quantity(dto.getQuantity())
                .allergens(allergens)
                .build();
    }
}
