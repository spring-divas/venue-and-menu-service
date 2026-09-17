package org.spring.divas.venue.feature.allergen;

import org.springframework.stereotype.Component;

@Component
public class AllergenResponseMapper {
    public AllergenResponseDto toDto(Allergen allergen) {
        return AllergenResponseDto.builder()
                .id(allergen.getId())
                .name(allergen.getName())
                .build();
    }
}
