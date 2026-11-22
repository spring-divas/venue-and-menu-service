package org.spring.divas.venue.feature.allergen;

import org.springframework.stereotype.Component;

@Component
public class AllergenResponseMapper {
    public AllergenResponseDto toDto(Allergen allergen) {
        return new AllergenResponseDto(allergen.getId(), allergen.getName());
    }
}
