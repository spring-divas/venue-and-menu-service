package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.spring.divas.venue.common.exception.ResourceNotFoundMessages;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientRepository;
import org.spring.divas.venue.feature.menu.MenuChangedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final DishChangePublisher dishChangePublisher;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public DishResponseDto create(DishRequestDto dto) {
        Dish dish = dishRequestMapper.toEntity(dto);
        Dish saved = dishRepository.save(dish);
        dishChangePublisher.publishCreated(saved);
        eventPublisher.publishEvent(new MenuChangedEvent(saved.getVenueId()));
        return dishResponseMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DishResponseDto> getAll() {
        return dishRepository.findAll().stream().map(
                dishResponseMapper::toDto
        ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DishResponseDto getById(Long id) {
        Dish found = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.DISH_NOT_FOUND));
        return dishResponseMapper.toDto(found);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DishResponseDto> getByIds(List<Long> ids) {
        return dishRepository.findAllById(ids).stream().map(
                dishResponseMapper::toDto
        ).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.DISH_NOT_FOUND));
        Long venueId = dish.getVenueId();
        dishRepository.deleteById(id);
        eventPublisher.publishEvent(new MenuChangedEvent(venueId));
    }

    @Override
    @Transactional
    public DishResponseDto update(Long id, DishRequestDto dto) {
        Dish found = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.DISH_NOT_FOUND));
        final Long previousVenueId = found.getVenueId();

        found.setVenueId(dto.venueId());

        Category category = categoryRepository.findById(dto.category())
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.CATEGORY_NOT_FOUND));
        found.setCategory(category);

        found.setName(dto.name());
        found.setDescription(dto.description());
        found.setPrice(dto.price());

        Set<Ingredient> ingredients =  new HashSet<>(
                ingredientRepository.findAllById(dto.ingredients()));
        if (ingredients.size() != dto.ingredients().size()) {
            throw new ResourceNotFoundException(ResourceNotFoundMessages.INGREDIENTS_NOT_FOUND);
        }
        found.setIngredients(ingredients);

        Dish saved = dishRepository.save(found);

        dishChangePublisher.publishUpdated(saved);

        eventPublisher.publishEvent(new MenuChangedEvent(saved.getVenueId()));

        if (!previousVenueId.equals(saved.getVenueId())) {
            eventPublisher.publishEvent(new MenuChangedEvent(previousVenueId));
        }

        return dishResponseMapper.toDto(saved);
    }

    @Override
    @Transactional
    public boolean updateRating(Long id, float rating, int reviewCount) {
        return dishRepository.findById(id)
                .map(dish -> {
                    dish.setRating(rating);
                    dish.setReviewCount(reviewCount);
                    return true;
                })
                .orElse(false);
    }
}
