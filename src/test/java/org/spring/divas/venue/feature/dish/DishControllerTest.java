package org.spring.divas.venue.feature.dish;

import static org.spring.divas.venue.feature.ControllerAssertions.assertResult;
import static org.spring.divas.venue.feature.ControllerFixtures.dishIds;
import static org.spring.divas.venue.feature.ControllerFixtures.dishes;
import static org.spring.divas.venue.feature.MapperFixtures.dishDto;
import static org.spring.divas.venue.feature.MapperFixtures.dishRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DishControllerTest {

    @Mock
    private DishService service;

    @InjectMocks
    private DishController controller;

    @Test
    void create_shouldDelegateToService() {
        DishRequestDto request = dishRequest();
        DishResponseDto response = dishDto();
        when(service.create(request)).thenReturn(response);
        assertResult(controller.create(request), response);
        verify(service).create(request);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(service.getAll()).thenReturn(dishes());
        assertResult(controller.getAll(), dishes());
        verify(service).getAll();
    }

    @Test
    void getById_shouldDelegateToService() {
        DishResponseDto response = dishDto();
        when(service.getById(response.id())).thenReturn(response);
        assertResult(controller.getById(response.id()), response);
        verify(service).getById(response.id());
    }

    @Test
    void getByIds_shouldDelegateToService() {
        when(service.getByIds(dishIds())).thenReturn(dishes());
        assertResult(controller.getByIds(dishIds()), dishes());
        verify(service).getByIds(dishIds());
    }

    @Test
    void update_shouldDelegateToService() {
        DishRequestDto request = dishRequest();
        DishResponseDto response = dishDto();
        when(service.update(response.id(), request)).thenReturn(response);
        assertResult(controller.update(response.id(), request), response);
        verify(service).update(response.id(), request);
    }

    @Test
    void delete_shouldDelegateToService() {
        controller.delete(dishDto().id());
        verify(service).delete(dishDto().id());
    }
}
