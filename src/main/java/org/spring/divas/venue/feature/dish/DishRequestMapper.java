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
        Set<Ingredient> ingredients = dto.getIngredients() == null
                ? new HashSet<>()
                : dto.getIngredients().stream()
                  .map(id -> ingredientRepository.findById(id).orElseThrow())
                  .collect(Collectors.toSet());
        return Dish.builder()
                .venueId(dto.getVenueId())
                .category(categoryRepository.findById(dto.getCategory()).orElseThrow())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .ingredients(ingredients)
                .build();
    }
}
