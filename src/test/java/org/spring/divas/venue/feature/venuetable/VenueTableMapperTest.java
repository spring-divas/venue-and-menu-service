package org.spring.divas.venue.feature.venuetable;

import static org.spring.divas.venue.feature.MapperAssertions.assertVenueTable;
import static org.spring.divas.venue.feature.MapperFixtures.venueTable;
import static org.spring.divas.venue.feature.MapperFixtures.venueTableRequest;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class VenueTableMapperTest {

    @Test
    void requestMapper_shouldMapDtoToEntity() {
        VenueTable entity = Mappers.getMapper(VenueTableRequestMapper.class).toEntity(venueTableRequest());
        assertVenueTable(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        VenueTableResponseDto dto = Mappers.getMapper(VenueTableResponseMapper.class).toDto(venueTable());
        assertVenueTable(dto);
    }
}
