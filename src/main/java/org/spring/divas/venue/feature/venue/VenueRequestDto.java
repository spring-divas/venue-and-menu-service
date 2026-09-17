package org.spring.divas.venue.feature.venue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueRequestDto {
    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String address;

    @PositiveOrZero
    private Float rating;

    @PositiveOrZero
    private Integer reviewCount;
}
