package org.spring.divas.venue.feature.category;

import static org.spring.divas.venue.feature.MapperAssertions.assertCategory;
import static org.spring.divas.venue.feature.MapperFixtures.category;
import static org.spring.divas.venue.feature.MapperFixtures.categoryRequest;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CategoryMapperTest {

    @Test
    void requestMapper_shouldMapDtoToEntity() {
        Category entity = Mappers.getMapper(CategoryRequestMapper.class).toEntity(categoryRequest());
        assertCategory(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        CategoryResponseDto dto = Mappers.getMapper(CategoryResponseMapper.class).toDto(category());
        assertCategory(dto);
    }
}
