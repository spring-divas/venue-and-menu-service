package org.spring.divas.venue.feature.venuetable;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VenueTableResponseMapper {

    VenueTableResponseDto toDto(VenueTable venueTable);
}
