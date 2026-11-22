package org.spring.divas.venue.feature.category;

import static org.spring.divas.venue.feature.ControllerAssertions.assertResult;
import static org.spring.divas.venue.feature.ControllerFixtures.categories;
import static org.spring.divas.venue.feature.MapperFixtures.categoryDto;
import static org.spring.divas.venue.feature.MapperFixtures.categoryRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService service;

    @InjectMocks
    private CategoryController controller;

    @Test
    void create_shouldDelegateToService() {
        CategoryRequestDto request = categoryRequest();
        CategoryResponseDto response = categoryDto();
        when(service.create(request)).thenReturn(response);
        assertResult(controller.create(request), response);
        verify(service).create(request);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(service.getAll()).thenReturn(categories());
        assertResult(controller.getAll(), categories());
        verify(service).getAll();
    }

    @Test
    void getById_shouldDelegateToService() {
        CategoryResponseDto response = categoryDto();
        when(service.getById(response.id())).thenReturn(response);
        assertResult(controller.getById(response.id()), response);
        verify(service).getById(response.id());
    }

    @Test
    void update_shouldDelegateToService() {
        CategoryRequestDto request = categoryRequest();
        CategoryResponseDto response = categoryDto();
        when(service.update(response.id(), request)).thenReturn(response);
        assertResult(controller.update(response.id(), request), response);
        verify(service).update(response.id(), request);
    }

    @Test
    void delete_shouldDelegateToService() {
        controller.delete(categoryDto().id());
        verify(service).delete(categoryDto().id());
    }
}
