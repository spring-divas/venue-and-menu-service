package org.spring.divas.venue.feature.dish;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final DishResponseMapper dishResponseMapper;
    private final DishRequestMapper dishRequestMapper;

    @Override
    public DishResponseDto create(DishRequestDto dto) {
        Dish dish = dishRequestMapper.toEntity(dto);
        Dish saved = dishRepository.save(dish);
        return dishResponseMapper.toDto(saved);
    }

    @Override
    public List<DishResponseDto> getAll() {
        return dishRepository.findAll().stream().map(
                dishResponseMapper::toDto
        ).toList();
    }

    @Override
    public DishResponseDto getById(Long id) {
        Dish found = dishRepository.findById(id)
                .orElseThrow();
        return dishResponseMapper.toDto(found);
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public DishResponseDto update(Long id, DishRequestDto dto) {
        Dish found = dishRepository.findById(id)
                .orElseThrow();
        found.setVenueId(dto.getVenueId());
        found.setName(dto.getName());
        found.setDescription(dto.getDescription());
        found.setPrice(dto.getPrice());
        found.setRating(dto.getRating());
        Dish saved = dishRepository.save(found);
        return dishResponseMapper.toDto(saved);
    }
}
