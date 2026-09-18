package org.spring.divas.venue.feature.venuetable;

import java.util.List;

public interface VenueTableService {
    VenueTableResponseDto create(VenueTableRequestDto dto);
    List<VenueTableResponseDto> getAll();
    VenueTableResponseDto getById(Long id);
    void delete(Long id);
    VenueTableResponseDto update(Long id, VenueTableRequestDto dto);
}
