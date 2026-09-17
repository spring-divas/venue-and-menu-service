package org.spring.divas.venue.feature.allergen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergenResponseDto {
    private Long id;
    private String name;
}
