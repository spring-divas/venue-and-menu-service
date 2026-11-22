package org.spring.divas.venue.feature.allergen;

import static org.spring.divas.venue.feature.MapperAssertions.assertAllergen;
import static org.spring.divas.venue.feature.MapperFixtures.allergen;
import static org.spring.divas.venue.feature.MapperFixtures.allergenRequest;

import org.junit.jupiter.api.Test;

class AllergenMapperTest {

    @Test
    void requestMapper_shouldMapNameToEntity() {
        Allergen entity = new AllergenRequestMapper().toEntity(allergenRequest());
        assertAllergen(entity);
    }

    @Test
    void responseMapper_shouldMapEntityToDto() {
        AllergenResponseDto dto = new AllergenResponseMapper().toDto(allergen());
        assertAllergen(dto);
    }
}
