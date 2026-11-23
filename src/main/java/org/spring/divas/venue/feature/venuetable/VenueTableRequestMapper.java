package org.spring.divas.venue.feature.venuetable;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VenueTableRequestMapper {

    @Mapping(target = "id", ignore = true)
    VenueTable toEntity(VenueTableRequestDto dto);
}
