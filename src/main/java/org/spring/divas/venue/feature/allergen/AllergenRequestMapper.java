package org.spring.divas.venue.feature.allergen;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AllergenRequestMapper {
    public Allergen toEntity(AllergenRequestDto dto) {
        return Allergen.builder()
                .name(dto.getName())
                .build();
    }
}
