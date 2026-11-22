package org.spring.divas.venue.feature.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.spring.divas.venue.feature.allergen.Allergen;
import org.spring.divas.venue.feature.allergen.AllergenRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private AllergenRepository allergenRepository;

    @Mock
    private IngredientResponseMapper ingredientResponseMapper;

    @Mock
    private IngredientRequestMapper ingredientRequestMapper;

    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(
                ingredientRepository, allergenRepository, ingredientResponseMapper, ingredientRequestMapper);
    }

    @Test
    void create_shouldReturnSavedDto() {
        IngredientRequestDto request = new IngredientRequestDto("Cheese", 100, Collections.emptySet());
        Ingredient entity = Ingredient.builder().id(1L).name("Cheese").quantity(100).build();
        IngredientResponseDto responseDto = new IngredientResponseDto(1L, "Cheese", 100, Collections.emptySet());

        when(ingredientRequestMapper.toEntity(request)).thenReturn(entity);
        when(ingredientRepository.save(entity)).thenReturn(entity);
        when(ingredientResponseMapper.toDto(entity)).thenReturn(responseDto);

        IngredientResponseDto result = ingredientService.create(request);

        assertThat(result).isEqualTo(responseDto);
        verify(ingredientRepository).save(entity);
    }

    @Test
    void getAll_shouldReturnAllIngredients() {
        Ingredient entity = Ingredient.builder().id(1L).name("Cheese").quantity(100).build();
        IngredientResponseDto responseDto = new IngredientResponseDto(1L, "Cheese", 100, Collections.emptySet());

        when(ingredientRepository.findAll()).thenReturn(List.of(entity));
        when(ingredientResponseMapper.toDto(entity)).thenReturn(responseDto);

        List<IngredientResponseDto> result = ingredientService.getAll();

        assertThat(result).containsExactly(responseDto);
    }

    @Test
    void getById_shouldReturnWhenFound() {
        Ingredient entity = Ingredient.builder().id(1L).name("Cheese").quantity(100).build();
        IngredientResponseDto responseDto = new IngredientResponseDto(1L, "Cheese", 100, Collections.emptySet());

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(ingredientResponseMapper.toDto(entity)).thenReturn(responseDto);

        IngredientResponseDto result = ingredientService.getById(1L);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(ingredientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ingredientService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Ingredient not found.");
    }

    @Test
    void delete_shouldCallRepository() {
        ingredientService.delete(1L);

        verify(ingredientRepository).deleteById(1L);
    }

    @Test
    void update_shouldUpdateFieldsAndAllergens() {
        Ingredient entity = Ingredient.builder().id(1L).name("Cheese").quantity(100).allergens(Collections.emptySet()).build();
        Allergen allergen = Allergen.builder().id(5L).name("Dairy").build();
        IngredientRequestDto request = new IngredientRequestDto("Swiss Cheese", 150, Set.of(5L));
        IngredientResponseDto responseDto = new IngredientResponseDto(1L, "Swiss Cheese", 150, Collections.emptySet());

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(allergenRepository.findAllById(Set.of(5L))).thenReturn(List.of(allergen));
        when(ingredientRepository.save(entity)).thenReturn(entity);
        when(ingredientResponseMapper.toDto(entity)).thenReturn(responseDto);

        IngredientResponseDto result = ingredientService.update(1L, request);

        assertThat(result).isEqualTo(responseDto);
        assertThat(entity.getName()).isEqualTo("Swiss Cheese");
        assertThat(entity.getQuantity()).isEqualTo(150);
        assertThat(entity.getAllergens()).contains(allergen);
    }

    @Test
    void update_shouldThrowWhenAllergenNotFound() {
        Ingredient entity = Ingredient.builder().id(1L).name("Cheese").quantity(100).build();
        IngredientRequestDto request = new IngredientRequestDto("Swiss Cheese", 150, Set.of(5L, 6L));

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(allergenRepository.findAllById(Set.of(5L, 6L))).thenReturn(List.of(Allergen.builder().id(5L).build()));

        assertThatThrownBy(() -> ingredientService.update(1L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("One or more allergens not found");
    }
}
