package org.spring.divas.venue.feature.menu;

import lombok.RequiredArgsConstructor;
import org.spring.divas.venue.feature.dish.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuMetricsService metricsService;
    private final DishRepository dishRepository;
    private final DishResponseMapper dishResponseMapper;

    @Override
    public List<DishResponseDto> getVenueMenu(Long venueId) {
        return metricsService.recordMenuRetrieval(
                () -> dishRepository.findByVenueId(venueId)
                        .stream()
                        .map(dishResponseMapper::toDto)
                        .toList());
    }
}
