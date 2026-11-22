package org.spring.divas.venue.feature.dish;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.spring.divas.venue.feature.category.Category;
import org.spring.divas.venue.feature.category.CategoryRepository;
import org.spring.divas.venue.feature.category.CategoryResponseDto;
import org.spring.divas.venue.feature.ingredient.IngredientRepository;
import org.spring.divas.venue.feature.menu.MenuChangedEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private DishResponseMapper dishResponseMapper;

    @Mock
    private DishRequestMapper dishRequestMapper;

    @Mock
    private DishChangePublisher dishChangePublisher;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private DishServiceImpl dishService;

    @BeforeEach
    void setUp() {
        dishService = new DishServiceImpl(
                dishRepository,
                categoryRepository,
                ingredientRepository,
                dishResponseMapper,
                dishRequestMapper,
                dishChangePublisher,
                eventPublisher
        );
    }

    @Test
    void create_shouldSaveDishPublishEventsAndReturnDto() {
        DishRequestDto request = new DishRequestDto(1L, 2L, "Pasta", "Delicious", BigDecimal.valueOf(12.5), Set.of());
        Dish entity = Dish.builder().id(100L).venueId(1L).name("Pasta").build();
        CategoryResponseDto categoryDto = new CategoryResponseDto(2L, 1L, "Category");
        DishResponseDto responseDto = new DishResponseDto(
                100L, 1L, categoryDto, "Pasta", "Delicious",
                BigDecimal.valueOf(12.5), 5.0f, 1, Set.of()
        );

        when(dishRequestMapper.toEntity(request)).thenReturn(entity);
        when(dishRepository.save(entity)).thenReturn(entity);
        when(dishResponseMapper.toDto(entity)).thenReturn(responseDto);

        DishResponseDto result = dishService.create(request);

        assertThat(result).isEqualTo(responseDto);
        verify(dishRepository).save(entity);
        verify(dishChangePublisher).publishCreated(entity);
        verify(eventPublisher).publishEvent(any(MenuChangedEvent.class));
    }

    @Test
    void getAll_shouldReturnAllDishes() {
        Dish entity = Dish.builder().id(100L).venueId(1L).name("Pasta").build();
        CategoryResponseDto categoryDto = new CategoryResponseDto(2L, 1L, "Category");
        DishResponseDto responseDto = new DishResponseDto(
                100L, 1L, categoryDto, "Pasta", "Delicious",
                BigDecimal.valueOf(12.5), 5.0f, 1, Set.of()
        );

        when(dishRepository.findAll()).thenReturn(List.of(entity));
        when(dishResponseMapper.toDto(entity)).thenReturn(responseDto);

        List<DishResponseDto> result = dishService.getAll();

        assertThat(result).containsExactly(responseDto);
    }

    @Test
    void getById_shouldReturnDishWhenFound() {
        Dish entity = Dish.builder().id(100L).venueId(1L).name("Pasta").build();
        CategoryResponseDto categoryDto = new CategoryResponseDto(2L, 1L, "Category");
        DishResponseDto responseDto = new DishResponseDto(
                100L, 1L, categoryDto, "Pasta", "Delicious",
                BigDecimal.valueOf(12.5), 5.0f, 1, Set.of()
        );

        when(dishRepository.findById(100L)).thenReturn(Optional.of(entity));
        when(dishResponseMapper.toDto(entity)).thenReturn(responseDto);

        DishResponseDto result = dishService.getById(100L);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(dishRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dishService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Dish not found.");
    }

    @Test
    void getByIds_shouldReturnMatchingDishes() {
        Dish entity = Dish.builder().id(100L).venueId(1L).name("Pasta").build();
        CategoryResponseDto categoryDto = new CategoryResponseDto(2L, 1L, "Category");
        DishResponseDto responseDto = new DishResponseDto(
                100L, 1L, categoryDto, "Pasta", "Delicious",
                BigDecimal.valueOf(12.5), 5.0f, 1, Set.of()
        );

        when(dishRepository.findAllById(List.of(100L))).thenReturn(List.of(entity));
        when(dishResponseMapper.toDto(entity)).thenReturn(responseDto);

        List<DishResponseDto> result = dishService.getByIds(List.of(100L));

        assertThat(result).containsExactly(responseDto);
    }

    @Test
    void delete_shouldDeleteAndPublishEvent() {
        Dish entity = Dish.builder().id(100L).venueId(1L).build();
        when(dishRepository.findById(100L)).thenReturn(Optional.of(entity));

        dishService.delete(100L);

        verify(dishRepository).deleteById(100L);
        verify(eventPublisher).publishEvent(any(MenuChangedEvent.class));
    }

    @Test
    void update_shouldUpdateFieldsPublishEventsAndReturnDto() {
        Category category = Category.builder().id(2L).build();
        Dish existing = Dish.builder().id(100L).venueId(1L).name("Old Pasta").build();
        DishRequestDto request = new DishRequestDto(2L, 2L, "New Pasta", "Fresh", BigDecimal.valueOf(15.0), Set.of());
        CategoryResponseDto categoryDto = new CategoryResponseDto(2L, 2L, "Category");
        DishResponseDto responseDto = new DishResponseDto(
                100L, 2L, categoryDto, "New Pasta", "Fresh",
                BigDecimal.valueOf(15.0), 5.0f, 1, Set.of()
        );

        when(dishRepository.findById(100L)).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(ingredientRepository.findAllById(Set.of())).thenReturn(Collections.emptyList());
        when(dishRepository.save(existing)).thenReturn(existing);
        when(dishResponseMapper.toDto(existing)).thenReturn(responseDto);

        DishResponseDto result = dishService.update(100L, request);

        assertThat(result).isEqualTo(responseDto);
        assertThat(existing.getName()).isEqualTo("New Pasta");
        assertThat(existing.getVenueId()).isEqualTo(2L);
        verify(dishChangePublisher).publishUpdated(existing);
        // Because venueId changed from 1 to 2, it publishes 2 events
        verify(eventPublisher, times(2)).publishEvent(any(MenuChangedEvent.class));
    }

    @Test
    void updateRating_shouldUpdateAndReturnTrue() {
        Dish dish = Dish.builder().id(100L).rating(3.0f).reviewCount(1).build();
        when(dishRepository.findById(100L)).thenReturn(Optional.of(dish));

        boolean updated = dishService.updateRating(100L, 4.8f, 5);

        assertThat(updated).isTrue();
        assertThat(dish.getRating()).isEqualTo(4.8f);
        assertThat(dish.getReviewCount()).isEqualTo(5);
    }

    @Test
    void updateRating_shouldReturnFalseWhenNotFound() {
        when(dishRepository.findById(99L)).thenReturn(Optional.empty());

        boolean updated = dishService.updateRating(99L, 4.8f, 5);

        assertThat(updated).isFalse();
    }
}
