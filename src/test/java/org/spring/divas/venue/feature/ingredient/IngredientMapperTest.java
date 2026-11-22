package org.spring.divas.venue.feature.ingredient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.spring.divas.venue.feature.MapperAssertions.assertIngredient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.spring.divas.venue.feature.MapperFixtures.allergen;
import static org.spring.divas.venue.feature.MapperFixtures.allergenDto;
import static org.spring.divas.venue.feature.MapperFixtures.ingredient;
import static org.spring.divas.venue.feature.MapperFixtures.ingredientRequest;
import static org.spring.divas.venue.feature.MapperFixtures.ingredientRequestWithoutAllergens;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.spring.divas.venue.feature.allergen.Allergen;
import org.spring.divas.venue.feature.allergen.AllergenRepository;
import org.spring.divas.venue.feature.allergen.AllergenResponseDto;
import org.spring.divas.venue.feature.allergen.AllergenResponseMapper;

class IngredientMapperTest {

    @Test
    void requestMapper_shouldResolveAllergensAndMapDto() {
        AllergenRepository repository = mock(AllergenRepository.class);
        Allergen allergen = allergen();
        when(repository.findById(allergen.getId())).thenReturn(Optional.of(allergen));
        IngredientRequestMapper mapper = new IngredientRequestMapper(repository);

        Ingredient entity = mapper.toEntity(ingredientRequest());

        assertIngredient(entity, Set.of(allergen));
        verify(repository).findById(allergen.getId());
    }

    @Test
    void requestMapper_shouldMapEmptyAllergens() {
        AllergenRepository repository = mock(AllergenRepository.class);
        IngredientRequestMapper mapper = new IngredientRequestMapper(repository);

        Ingredient entity = mapper.toEntity(ingredientRequestWithoutAllergens());

        assertThat(entity.getAllergens()).isEmpty();
        verifyNoInteractions(repository);
    }

    @Test
    void responseMapper_shouldMapAllFieldsAndAllergens() {
        AllergenResponseMapper allergenMapper = mock(AllergenResponseMapper.class);
        Allergen allergen = allergen();
        AllergenResponseDto allergenDto = allergenDto();
        when(allergenMapper.toDto(allergen)).thenReturn(allergenDto);
        Ingredient ingredient = ingredient();
        ingredient.setAllergens(Set.of(allergen));

        IngredientResponseDto dto = new IngredientResponseMapper(allergenMapper).toDto(ingredient);

        assertIngredient(dto, Set.of(allergenDto));
        verify(allergenMapper).toDto(allergen);
    }

    @Test
    void responseMapper_shouldUseEmptySetWhenAllergensAreNull() {
        Ingredient ingredient = ingredient();
        ingredient.setAllergens(null);
        AllergenResponseMapper allergenMapper = mock(AllergenResponseMapper.class);

        IngredientResponseDto dto = new IngredientResponseMapper(allergenMapper).toDto(ingredient);

        assertThat(dto.allergens()).isEmpty();
        verifyNoInteractions(allergenMapper);
    }
}
