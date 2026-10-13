package org.spring.divas.venue.feature.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryResponseMapper {
    public CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .venueId(category.getVenueId())
                .name(category.getName())
                .build();
    }
}
