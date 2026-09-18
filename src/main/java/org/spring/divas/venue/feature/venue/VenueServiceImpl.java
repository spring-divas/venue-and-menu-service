package org.spring.divas.venue.feature.venue;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VenueServiceImpl implements VenueService {
    private final VenueRepository venueRepository;
    private final VenueResponseMapper venueResponseMapper;
    private final VenueRequestMapper venueRequestMapper;

    @Override
    public VenueResponseDto create(VenueRequestDto dto) {
        Venue venue = venueRequestMapper.toEntity(dto);
        Venue saved = venueRepository.save(venue);
        return venueResponseMapper.toDto(saved);
    }

    @Override
    public List<VenueResponseDto> getAll() {
        return venueRepository.findAll().stream().map(
                venueResponseMapper::toDto
        ).toList();
    }

    @Override
    public VenueResponseDto getById(Long id) {
        Venue found = venueRepository.findById(id)
                .orElseThrow();
        return venueResponseMapper.toDto(found);
    }

    @Override
    public void delete(Long id) {
        venueRepository.deleteById(id);
    }

    @Override
    public VenueResponseDto update(Long id, VenueRequestDto dto) {
        Venue found = venueRepository.findById(id)
                .orElseThrow();
        found.setName(dto.getName());
        found.setAddress(dto.getAddress());
        found.setRating(dto.getRating());
        found.setReviewCount(dto.getReviewCount());
        Venue saved = venueRepository.save(found);
        return venueResponseMapper.toDto(saved);
    }
}
