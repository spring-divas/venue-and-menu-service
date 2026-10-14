package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientRepository;
import org.spring.divas.venue.feature.menu.MenuChangedEvent;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import org.spring.divas.venue.feature.outbox.OutboxRepository;
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
    private final OutboxRepository outboxRepository;

    private final DishResponseMapper dishResponseMapper;
    private final DishRequestMapper dishRequestMapper;
    private final DishEventMapper dishEventMapper;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public DishResponseDto create(DishRequestDto dto) {
        Dish dish = dishRequestMapper.toEntity(dto);
        Dish saved = dishRepository.save(dish);
        DishUpdatedEvent event = dishEventMapper.toUpdatedEvent(saved);
        OutboxEvent outbox = dishEventMapper.toOutboxEvent(event);
        outboxRepository.save(outbox);
        eventPublisher.publishEvent(new MenuChangedEvent(saved.getVenueId()));
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
    public List<DishResponseDto> getByIds(List<Long> ids) {
        return dishRepository.findAllById(ids).stream().map(
                dishResponseMapper::toDto
        ).toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found."));
        Long venueId = dish.getVenueId();
        dishRepository.deleteById(id);
        eventPublisher.publishEvent(new MenuChangedEvent(venueId));
    }

    @Override
    @Transactional
    public DishResponseDto update(Long id, DishRequestDto dto) {
        Dish found = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found."));
        Long previousVenueId = found.getVenueId();

        found.setVenueId(dto.getVenueId());

        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        found.setCategory(category);

        found.setName(dto.getName());
        found.setDescription(dto.getDescription());
        found.setPrice(dto.getPrice());

        Set<Ingredient> ingredients =  new HashSet<>(
                ingredientRepository.findAllById(dto.getIngredients()));
        if (ingredients.size() != dto.getIngredients().size()) {
            throw new ResourceNotFoundException("One or more ingredients not found");
        }
        found.setIngredients(ingredients);

        Dish saved = dishRepository.save(found);

        DishUpdatedEvent event = dishEventMapper.toUpdatedEvent(saved);
        OutboxEvent outbox = dishEventMapper.toOutboxEvent(event);
        outboxRepository.save(outbox);

        eventPublisher.publishEvent(new MenuChangedEvent(saved.getVenueId()));

        if (!previousVenueId.equals(saved.getVenueId())) {
            eventPublisher.publishEvent(new MenuChangedEvent(previousVenueId));
        }

        return dishResponseMapper.toDto(saved);
    }
}
