package org.spring.divas.venue.feature.allergen;

import static org.spring.divas.venue.feature.ControllerAssertions.assertResult;
import static org.spring.divas.venue.feature.ControllerFixtures.allergens;
import static org.spring.divas.venue.feature.MapperFixtures.allergenDto;
import static org.spring.divas.venue.feature.MapperFixtures.allergenRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AllergenControllerTest {

    @Mock
    private AllergenService service;

    @InjectMocks
    private AllergenController controller;

    @Test
    void create_shouldDelegateToService() {
        AllergenRequestDto request = allergenRequest();
        AllergenResponseDto response = allergenDto();
        when(service.create(request)).thenReturn(response);
        assertResult(controller.create(request), response);
        verify(service).create(request);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(service.getAll()).thenReturn(allergens());
        assertResult(controller.getAll(), allergens());
        verify(service).getAll();
    }

    @Test
    void getById_shouldDelegateToService() {
        AllergenResponseDto response = allergenDto();
        when(service.getById(response.id())).thenReturn(response);
        assertResult(controller.getById(response.id()), response);
        verify(service).getById(response.id());
    }

    @Test
    void update_shouldDelegateToService() {
        AllergenRequestDto request = allergenRequest();
        AllergenResponseDto response = allergenDto();
        when(service.update(response.id(), request)).thenReturn(response);
        assertResult(controller.update(response.id(), request), response);
        verify(service).update(response.id(), request);
    }

    @Test
    void delete_shouldDelegateToService() {
        controller.delete(allergenDto().id());
        verify(service).delete(allergenDto().id());
    }
}
