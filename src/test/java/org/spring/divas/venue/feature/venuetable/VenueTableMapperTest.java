package org.spring.divas.venue.feature.venuetable;

import static org.spring.divas.venue.feature.MapperAssertions.assertVenueTable;
import static org.spring.divas.venue.feature.MapperFixtures.venueTable;
import static org.spring.divas.venue.feature.MapperFixtures.venueTableRequest;

import org.junit.jupiter.api.Test;

class VenueTableMapperTest {

    @Test
    void requestMapper_shouldMapDtoToEntity() {
        VenueTable entity = new VenueTableRequestMapper().toEntity(venueTableRequest());
        assertVenueTable(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        VenueTableResponseDto dto = new VenueTableResponseMapper().toDto(venueTable());
        assertVenueTable(dto);
    }
}
