package org.spring.divas.venue.feature.allergen;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AllergenServiceImpl implements AllergenService {
    private final AllergenRepository allergenRepository;
    private final AllergenRequestMapper allergenRequestMapper;
    private final AllergenResponseMapper allergenResponseMapper;

    @Override
    public AllergenResponseDto create(AllergenRequestDto dto) {
        Allergen allergen = allergenRequestMapper.toEntity(dto);
        Allergen saved = allergenRepository.save(allergen);
        return allergenResponseMapper.toDto(saved);
    }

    @Override
    public List<AllergenResponseDto> getAll() {
        return allergenRepository.findAll().stream().map(
                allergenResponseMapper::toDto
        ).toList();
    }

    @Override
    public AllergenResponseDto getById(Long id) {
        Allergen found = allergenRepository.findById(id)
                .orElseThrow();
        return allergenResponseMapper.toDto(found);
    }

    @Override
    public void delete(Long id) {
        allergenRepository.deleteById(id);
    }

    @Override
    public AllergenResponseDto update(Long id, AllergenRequestDto dto) {
        Allergen allergen = allergenRepository.findById(id).orElseThrow();
        allergen.setName(dto.getName());
        Allergen saved = allergenRepository.save(allergen);
        return allergenResponseMapper.toDto(saved);
    }
}
