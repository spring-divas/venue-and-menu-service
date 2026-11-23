package org.spring.divas.venue.feature.venue;

import static org.spring.divas.venue.feature.MapperAssertions.assertVenue;
import static org.spring.divas.venue.feature.MapperFixtures.venue;
import static org.spring.divas.venue.feature.MapperFixtures.venueRequest;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class VenueMapperTest {

    @Test
    void requestMapper_shouldMapDtoToEntity() {
        Venue entity = Mappers.getMapper(VenueRequestMapper.class).toEntity(venueRequest());
        assertVenue(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        VenueResponseDto dto = Mappers.getMapper(VenueResponseMapper.class).toDto(venue());
        assertVenue(dto);
    }
}
