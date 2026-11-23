package org.spring.divas.venue.feature.allergen;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AllergenResponseMapper {

    AllergenResponseDto toDto(Allergen allergen);
}
