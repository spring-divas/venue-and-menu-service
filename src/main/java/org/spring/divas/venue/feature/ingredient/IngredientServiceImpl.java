package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.feature.allergen.Allergen;
import org.spring.divas.venue.feature.allergen.AllergenRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;
    private final AllergenRepository allergenRepository;

    private final IngredientResponseMapper ingredientResponseMapper;
    private final IngredientRequestMapper ingredientRequestMapper;

    @Override
    public IngredientResponseDto create(IngredientRequestDto dto) {
        Ingredient ingredient = ingredientRequestMapper.toEntity(dto);
        Ingredient saved = ingredientRepository.save(ingredient);
        return ingredientResponseMapper.toDto(saved);
    }

    @Override
    public List<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll().stream().map(
                ingredientResponseMapper::toDto
        ).toList();
    }

    @Override
    public IngredientResponseDto getById(Long id) {
        Ingredient found = ingredientRepository.findById(id)
                .orElseThrow();
        return ingredientResponseMapper.toDto(found);
    }

    @Override
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    public IngredientResponseDto update(Long id, IngredientRequestDto dto) {
        Ingredient found = ingredientRepository.findById(id)
                .orElseThrow();
        found.setName(dto.getName());
        found.setQuantity(dto.getQuantity());

        Set<Allergen> allergens = new HashSet<>(
                allergenRepository.findAllById(dto.getAllergens()));
        found.setAllergens(allergens);

        Ingredient saved = ingredientRepository.save(found);
        return ingredientResponseMapper.toDto(saved);
    }
}
