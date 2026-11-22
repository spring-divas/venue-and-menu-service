package org.spring.divas.venue.feature.venuetable;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.spring.divas.venue.common.exception.ResourceNotFoundMessages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VenueTableServiceImpl implements VenueTableService {
    private final VenueTableRepository venueTableRepository;
    private final VenueTableResponseMapper venueTableResponseMapper;
    private final VenueTableRequestMapper venueTableRequestMapper;

    @Override
    public VenueTableResponseDto create(VenueTableRequestDto dto) {
        VenueTable table = venueTableRequestMapper.toEntity(dto);
        VenueTable saved = venueTableRepository.save(table);
        return venueTableResponseMapper.toDto(saved);
    }

    @Override
    public List<VenueTableResponseDto> getAll() {
        return venueTableRepository.findAll().stream().map(
                venueTableResponseMapper::toDto
        ).toList();
    }

    @Override
    public VenueTableResponseDto getById(Long id) {
        VenueTable found = venueTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.TABLE_NOT_FOUND));
        return venueTableResponseMapper.toDto(found);
    }

    @Override
    public void delete(Long id) {
        venueTableRepository.deleteById(id);
    }

    @Override
    public VenueTableResponseDto update(Long id, VenueTableRequestDto dto) {
        VenueTable found = venueTableRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.TABLE_NOT_FOUND));
        found.setVenueId(dto.venueId());
        found.setIsAvailable(dto.isAvailable());
        VenueTable saved = venueTableRepository.save(found);
        return venueTableResponseMapper.toDto(saved);
    }
}
