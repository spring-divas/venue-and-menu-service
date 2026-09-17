package org.spring.divas.venue.feature.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.divas.venue.feature.allergen.AllergenRequestDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientRequestDto {
    @NotBlank
    @Size(max = 100)
    private String name;

    @PositiveOrZero
    private Integer quantity;
    private Set<Long> allergens;
}
