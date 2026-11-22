package org.spring.divas.venue.feature.venuetable;

import static org.spring.divas.venue.feature.ControllerAssertions.assertResult;
import static org.spring.divas.venue.feature.ControllerFixtures.venueTables;
import static org.spring.divas.venue.feature.MapperFixtures.venueTableDto;
import static org.spring.divas.venue.feature.MapperFixtures.venueTableRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VenueTableControllerTest {

    @Mock
    private VenueTableService service;

    @InjectMocks
    private VenueTableController controller;

    @Test
    void create_shouldDelegateToService() {
        VenueTableRequestDto request = venueTableRequest();
        VenueTableResponseDto response = venueTableDto();
        when(service.create(request)).thenReturn(response);
        assertResult(controller.create(request), response);
        verify(service).create(request);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(service.getAll()).thenReturn(venueTables());
        assertResult(controller.getAll(), venueTables());
        verify(service).getAll();
    }

    @Test
    void getById_shouldDelegateToService() {
        VenueTableResponseDto response = venueTableDto();
        when(service.getById(response.id())).thenReturn(response);
        assertResult(controller.getById(response.id()), response);
        verify(service).getById(response.id());
    }

    @Test
    void update_shouldDelegateToService() {
        VenueTableRequestDto request = venueTableRequest();
        VenueTableResponseDto response = venueTableDto();
        when(service.update(response.id(), request)).thenReturn(response);
        assertResult(controller.update(response.id(), request), response);
        verify(service).update(response.id(), request);
    }

    @Test
    void delete_shouldDelegateToService() {
        controller.delete(venueTableDto().id());
        verify(service).delete(venueTableDto().id());
    }
}
