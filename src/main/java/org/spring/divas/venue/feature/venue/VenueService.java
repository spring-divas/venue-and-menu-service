package org.spring.divas.venue.feature.venue;

import java.util.List;

public interface VenueService {
    VenueResponseDto create(VenueRequestDto dto);
    List<VenueResponseDto> getAll();
    VenueResponseDto getById(Long id);
    void delete(Long id);
    VenueResponseDto update(Long id, VenueRequestDto dto);
}
