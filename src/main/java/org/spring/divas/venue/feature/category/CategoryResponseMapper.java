package org.spring.divas.venue.feature.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryResponseMapper {
    public CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getVenueId(), category.getName());
    }
}
