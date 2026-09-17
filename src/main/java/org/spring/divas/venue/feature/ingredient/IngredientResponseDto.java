package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.divas.venue.feature.allergen.Allergen;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientResponseDto {
    private Long id;
    private String name;
    private Integer quantity;
    private Set<Allergen> allergens;
}
