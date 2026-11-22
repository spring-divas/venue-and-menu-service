package org.spring.divas.venue.feature.ingredient;

import static org.spring.divas.venue.feature.ControllerAssertions.assertResult;
import static org.spring.divas.venue.feature.ControllerFixtures.ingredients;
import static org.spring.divas.venue.feature.MapperFixtures.ingredientDto;
import static org.spring.divas.venue.feature.MapperFixtures.ingredientRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService service;

    @InjectMocks
    private IngredientController controller;

    @Test
    void create_shouldDelegateToService() {
        IngredientRequestDto request = ingredientRequest();
        IngredientResponseDto response = ingredientDto();
        when(service.create(request)).thenReturn(response);
        assertResult(controller.create(request), response);
        verify(service).create(request);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(service.getAll()).thenReturn(ingredients());
        assertResult(controller.getAll(), ingredients());
        verify(service).getAll();
    }

    @Test
    void getById_shouldDelegateToService() {
        IngredientResponseDto response = ingredientDto();
        when(service.getById(response.id())).thenReturn(response);
        assertResult(controller.getById(response.id()), response);
        verify(service).getById(response.id());
    }

    @Test
    void update_shouldDelegateToService() {
        IngredientRequestDto request = ingredientRequest();
        IngredientResponseDto response = ingredientDto();
        when(service.update(response.id(), request)).thenReturn(response);
        assertResult(controller.update(response.id(), request), response);
        verify(service).update(response.id(), request);
    }

    @Test
    void delete_shouldDelegateToService() {
        controller.delete(ingredientDto().id());
        verify(service).delete(ingredientDto().id());
    }
}
