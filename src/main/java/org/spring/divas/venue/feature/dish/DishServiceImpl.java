package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;

    private final DishResponseMapper dishResponseMapper;
    private final DishRequestMapper dishRequestMapper;

    @Override
    public DishResponseDto create(DishRequestDto dto) {
        Dish dish = dishRequestMapper.toEntity(dto);
        Dish saved = dishRepository.save(dish);
        return dishResponseMapper.toDto(saved);
    }

    @Override
    public List<DishResponseDto> getAll() {
        return dishRepository.findAll().stream().map(
                dishResponseMapper::toDto
        ).toList();
    }

    @Override
    public DishResponseDto getById(Long id) {
        Dish found = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found."));
        return dishResponseMapper.toDto(found);
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public DishResponseDto update(Long id, DishRequestDto dto) {
        Dish found = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found."));
        found.setVenueId(dto.getVenueId());

        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        found.setCategory(category);

        found.setName(dto.getName());
        found.setDescription(dto.getDescription());
        found.setPrice(dto.getPrice());
        found.setRating(dto.getRating());
        found.setReviewCount(dto.getReviewCount());

        Set<Ingredient> ingredients =  new HashSet<>(
                ingredientRepository.findAllById(dto.getIngredients()));
        found.setIngredients(ingredients);

        Dish saved = dishRepository.save(found);
        return dishResponseMapper.toDto(saved);
    }
}
