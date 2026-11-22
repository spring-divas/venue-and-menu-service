package org.spring.divas.venue.feature.category;

import static org.spring.divas.venue.feature.MapperAssertions.assertCategory;
import static org.spring.divas.venue.feature.MapperFixtures.category;
import static org.spring.divas.venue.feature.MapperFixtures.categoryRequest;

import org.junit.jupiter.api.Test;

class CategoryMapperTest {

    @Test
    void requestMapper_shouldMapDtoToEntity() {
        Category entity = new CategoryRequestMapper().toEntity(categoryRequest());
        assertCategory(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        CategoryResponseDto dto = new CategoryResponseMapper().toDto(category());
        assertCategory(dto);
    }
}
