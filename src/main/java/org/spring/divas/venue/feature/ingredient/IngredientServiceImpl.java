package org.spring.divas.venue.feature.ingredient;

import lombok.AllArgsConstructor;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;
import org.spring.divas.venue.common.exception.ResourceNotFoundMessages;
import org.spring.divas.venue.feature.allergen.Allergen;
import org.spring.divas.venue.feature.allergen.AllergenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public IngredientResponseDto create(IngredientRequestDto dto) {
        Ingredient ingredient = ingredientRequestMapper.toEntity(dto);
        Ingredient saved = ingredientRepository.save(ingredient);
        return ingredientResponseMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientResponseDto> getAll() {
        return ingredientRepository.findAll().stream().map(
                ingredientResponseMapper::toDto
        ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDto getById(Long id) {
        Ingredient found = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.INGREDIENT_NOT_FOUND));
        return ingredientResponseMapper.toDto(found);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public IngredientResponseDto update(Long id, IngredientRequestDto dto) {
        Ingredient found = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundMessages.INGREDIENT_NOT_FOUND));
        found.setName(dto.name());
        found.setQuantity(dto.quantity());

        Set<Long> allergenIds = dto.allergens() == null ? Set.of() : dto.allergens();
        Set<Allergen> allergens = new HashSet<>(
                allergenRepository.findAllById(allergenIds));
        if (allergens.size() != allergenIds.size()) {
            throw new ResourceNotFoundException(ResourceNotFoundMessages.ALLERGENS_NOT_FOUND);
        }
        found.setAllergens(allergens);

        Ingredient saved = ingredientRepository.save(found);
        return ingredientResponseMapper.toDto(saved);
    }
}
