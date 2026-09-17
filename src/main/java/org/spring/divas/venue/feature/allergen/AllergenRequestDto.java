package org.spring.divas.venue.feature.allergen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllergenRequestDto {
    @NotBlank
    @Size(max = 100)
    private String name;
}
