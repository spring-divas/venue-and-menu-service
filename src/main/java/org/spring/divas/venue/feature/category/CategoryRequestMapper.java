package org.spring.divas.venue.feature.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryRequestMapper {
    public Category toEntity(CategoryRequestDto dto) {
        return Category.builder()
                .venueId(dto.getVenueId())
                .name(dto.getName())
                .build();
    }
}
