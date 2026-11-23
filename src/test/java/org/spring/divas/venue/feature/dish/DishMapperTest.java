package org.spring.divas.venue.feature.dish;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.spring.divas.venue.feature.MapperAssertions.assertDish;
import static org.spring.divas.venue.feature.MapperAssertions.assertCreatedEvent;
import static org.spring.divas.venue.feature.MapperAssertions.assertCreatedOutbox;
import static org.spring.divas.venue.feature.MapperAssertions.assertUpdatedEvent;
import static org.spring.divas.venue.feature.MapperAssertions.assertUpdatedOutbox;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.spring.divas.venue.feature.MapperFixtures.category;
import static org.spring.divas.venue.feature.MapperFixtures.categoryDto;
import static org.spring.divas.venue.feature.MapperFixtures.createdEvent;
import static org.spring.divas.venue.feature.MapperFixtures.dish;
import static org.spring.divas.venue.feature.MapperFixtures.dishRequest;
import static org.spring.divas.venue.feature.MapperFixtures.dishRequestWithMissingCategory;
import static org.spring.divas.venue.feature.MapperFixtures.ingredient;
import static org.spring.divas.venue.feature.MapperFixtures.ingredientDto;
import static org.spring.divas.venue.feature.MapperFixtures.updatedEvent;
import static org.spring.divas.venue.feature.MapperFixtures.updatedPayload;
import static org.spring.divas.venue.feature.MapperFixtures.createdPayload;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.category.CategoryResponseMapper;
import org.spring.divas.venue.feature.ingredient.Ingredient;
import org.spring.divas.venue.feature.ingredient.IngredientRepository;
import org.spring.divas.venue.feature.ingredient.IngredientResponseDto;
import org.spring.divas.venue.feature.ingredient.IngredientResponseMapper;
import org.spring.divas.venue.feature.outbox.OutboxEvent;
import tools.jackson.databind.ObjectMapper;

class DishMapperTest {

    @Test
    void requestMapper_shouldResolveRelationsAndMapDto() {
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        Category category = category();
        Ingredient ingredient = ingredient();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(ingredientRepository.findById(ingredient.getId())).thenReturn(Optional.of(ingredient));
        DishRequestMapper mapper = requestMapper(categoryRepository, ingredientRepository);

        Dish entity = mapper.toEntity(dishRequest());

        assertDish(entity, category, Set.of(ingredient));
        verify(categoryRepository).findById(category.getId());
        verify(ingredientRepository).findById(ingredient.getId());
    }

    @Test
    void requestMapper_shouldThrowWhenCategoryIsMissing() {
        CategoryRepository categoryRepository = mock(CategoryRepository.class);
        IngredientRepository ingredientRepository = mock(IngredientRepository.class);
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());
        DishRequestMapper mapper = requestMapper(categoryRepository, ingredientRepository);

        assertThatThrownBy(() -> mapper.toEntity(dishRequestWithMissingCategory()))
                .isInstanceOf(java.util.NoSuchElementException.class);

        verify(categoryRepository).findById(2L);
        verifyNoInteractions(ingredientRepository);
    }

    @Test
    void responseMapper_shouldMapAllFieldsAndRelations() {
        CategoryResponseMapper categoryMapper = mock(CategoryResponseMapper.class);
        IngredientResponseMapper ingredientMapper = mock(IngredientResponseMapper.class);
        Category category = category();
        Ingredient ingredient = ingredient();
        CategoryResponseDto categoryDto = categoryDto();
        IngredientResponseDto ingredientDto = ingredientDto();
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);
        when(ingredientMapper.toDto(ingredient)).thenReturn(ingredientDto);
        Dish dish = dish();
        dish.setCategory(category);
        dish.setIngredients(Set.of(ingredient));

        DishResponseDto dto = new DishResponseMapperImpl(categoryMapper, ingredientMapper).toDto(dish);

        assertDish(dto, categoryDto, Set.of(ingredientDto));
        verify(categoryMapper).toDto(category);
        verify(ingredientMapper).toDto(ingredient);
    }

    @Test
    void responseMapper_shouldUseEmptyIngredientsWhenTheyAreNull() {
        CategoryResponseMapper categoryMapper = mock(CategoryResponseMapper.class);
        IngredientResponseMapper ingredientMapper = mock(IngredientResponseMapper.class);
        Category category = category();
        when(categoryMapper.toDto(category)).thenReturn(categoryDto());
        Dish dish = dish();
        dish.setIngredients(null);

        DishResponseDto dto = new DishResponseMapperImpl(categoryMapper, ingredientMapper).toDto(dish);

        assertThat(dto.ingredients()).isEmpty();
        verifyNoInteractions(ingredientMapper);
    }

    @Test
    void eventMapper_shouldCreateEventsAndMapUpdatedEventToOutbox() {
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        DishEventMapper mapper = eventMapper(objectMapper);
        Dish dish = dish();

        DishUpdatedEvent updated = mapper.toUpdatedEvent(dish);
        DishCreatedEvent created = mapper.toCreatedEvent(dish);
        DishUpdatedEvent event = updatedEvent();
        when(objectMapper.writeValueAsString(event)).thenReturn(updatedPayload());

        OutboxEvent outbox = mapper.toOutboxEvent(event);

        assertUpdatedEvent(updated);
        assertCreatedEvent(created);
        assertUpdatedOutbox(outbox, event);
        verify(objectMapper).writeValueAsString(event);
    }

    @Test
    void eventMapper_shouldMapCreatedEventToOutbox() {
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        DishEventMapper mapper = eventMapper(objectMapper);
        DishCreatedEvent event = createdEvent();
        when(objectMapper.writeValueAsString(event)).thenReturn(createdPayload());

        OutboxEvent outbox = mapper.toOutboxEvent(event);

        assertCreatedOutbox(outbox, event);
        verify(objectMapper).writeValueAsString(event);
    }

    private static DishRequestMapper requestMapper(
            CategoryRepository categoryRepository,
            IngredientRepository ingredientRepository
    ) {
        DishRequestMapperImpl mapper = new DishRequestMapperImpl();
        mapper.setCategoryRepository(categoryRepository);
        mapper.setIngredientRepository(ingredientRepository);
        return mapper;
    }

    private static DishEventMapper eventMapper(ObjectMapper objectMapper) {
        DishEventMapperImpl mapper = new DishEventMapperImpl();
        mapper.setObjectMapper(objectMapper);
        return mapper;
    }
}
