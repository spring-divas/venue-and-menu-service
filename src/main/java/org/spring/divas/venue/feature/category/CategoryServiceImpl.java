package org.spring.divas.venue.feature.category;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public CategoryResponseDto create(CategoryRequestDto dto) {
        Category category = categoryRequestMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        return categoryResponseMapper.toDto(saved);
    }

    @Override
    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll().stream().map(
                categoryResponseMapper::toDto
        ).toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category found = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        return categoryResponseMapper.toDto(found);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto dto) {
        Category found = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        found.setVenueId(dto.getVenueId());
        found.setName(dto.getName());
        Category saved = categoryRepository.save(found);
        return categoryResponseMapper.toDto(saved);
    }
}
