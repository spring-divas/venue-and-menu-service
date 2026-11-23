package org.spring.divas.venue.feature.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDto dto);
}
