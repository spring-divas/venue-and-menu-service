package org.spring.divas.venue.feature.allergen;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AllergenRequestMapper {

    @Mapping(target = "id", ignore = true)
    Allergen toEntity(AllergenRequestDto dto);
}
