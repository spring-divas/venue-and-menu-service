package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DishRequestMapper {
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;

    public Dish toEntity(DishRequestDto dto) {
        Set<Ingredient> ingredients = dto.ingredients() == null
                ? new HashSet<>()
                : dto.ingredients().stream()
                .map(id -> ingredientRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
        return Dish.builder()
                .venueId(dto.venueId())
                .category(categoryRepository.findById(dto.category()).orElseThrow())
                .name(dto.name())
                .description(dto.description())
                .price(dto.price())
                .ingredients(ingredients)
                .build();
    }
}
