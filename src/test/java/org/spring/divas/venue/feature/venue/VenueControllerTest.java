package org.spring.divas.venue.feature.venue;

import static org.spring.divas.venue.feature.ControllerAssertions.assertResult;
import static org.spring.divas.venue.feature.ControllerFixtures.venues;
import static org.spring.divas.venue.feature.MapperFixtures.venueDto;
import static org.spring.divas.venue.feature.MapperFixtures.venueRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VenueControllerTest {

    @Mock
    private VenueService service;

    @InjectMocks
    private VenueController controller;

    @Test
    void create_shouldDelegateToService() {
        VenueRequestDto request = venueRequest();
        VenueResponseDto response = venueDto();
        when(service.create(request)).thenReturn(response);
        assertResult(controller.create(request), response);
        verify(service).create(request);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(service.getAll()).thenReturn(venues());
        assertResult(controller.getAll(), venues());
        verify(service).getAll();
    }

    @Test
    void getById_shouldDelegateToService() {
        VenueResponseDto response = venueDto();
        when(service.getById(response.id())).thenReturn(response);
        assertResult(controller.getById(response.id()), response);
        verify(service).getById(response.id());
    }

    @Test
    void update_shouldDelegateToService() {
        VenueRequestDto request = venueRequest();
        VenueResponseDto response = venueDto();
        when(service.update(response.id(), request)).thenReturn(response);
        assertResult(controller.update(response.id(), request), response);
        verify(service).update(response.id(), request);
    }

    @Test
    void delete_shouldDelegateToService() {
        controller.delete(venueDto().id());
        verify(service).delete(venueDto().id());
    }
}
