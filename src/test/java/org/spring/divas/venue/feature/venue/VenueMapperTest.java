package org.spring.divas.venue.feature.venue;

import static org.spring.divas.venue.feature.MapperAssertions.assertVenue;
import static org.spring.divas.venue.feature.MapperFixtures.venue;
import static org.spring.divas.venue.feature.MapperFixtures.venueRequest;

import org.junit.jupiter.api.Test;

class VenueMapperTest {

    @Test
    void requestMapper_shouldMapDtoToEntity() {
        Venue entity = new VenueRequestMapper().toEntity(venueRequest());
        assertVenue(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        VenueResponseDto dto = new VenueResponseMapper().toDto(venue());
        assertVenue(dto);
    }
}
