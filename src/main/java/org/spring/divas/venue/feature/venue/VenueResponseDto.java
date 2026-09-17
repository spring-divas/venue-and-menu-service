package org.spring.divas.venue.feature.venue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VenueResponseDto {
    private Long id;
    private String name;
    private String address;
    private Float rating;
    private Integer reviewCount;
}
