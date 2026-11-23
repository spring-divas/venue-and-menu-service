package org.spring.divas.venue.feature.venue;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VenueResponseMapper {

    VenueResponseDto toDto(Venue venue);
}
