package org.spring.divas.venue.feature.allergen;

import static org.spring.divas.venue.feature.MapperAssertions.assertAllergen;
import static org.spring.divas.venue.feature.MapperFixtures.allergen;
import static org.spring.divas.venue.feature.MapperFixtures.allergenRequest;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AllergenMapperTest {

    @Test
    void requestMapper_shouldMapNameToEntity() {
        Allergen entity = Mappers.getMapper(AllergenRequestMapper.class).toEntity(allergenRequest());
        assertAllergen(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        AllergenResponseDto dto = Mappers.getMapper(AllergenResponseMapper.class).toDto(allergen());
        assertAllergen(dto);
    }
}
