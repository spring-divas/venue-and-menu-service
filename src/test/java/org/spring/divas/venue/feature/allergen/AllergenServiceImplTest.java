package org.spring.divas.venue.feature.allergen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.divas.venue.common.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllergenServiceImplTest {

    @Mock
    private AllergenRepository allergenRepository;

    @Mock
    private AllergenRequestMapper allergenRequestMapper;

    @Mock
    private AllergenResponseMapper allergenResponseMapper;

    private AllergenServiceImpl allergenService;

    @BeforeEach
    void setUp() {
        allergenService = new AllergenServiceImpl(allergenRepository, allergenRequestMapper, allergenResponseMapper);
    }

    @Test
    void create_shouldReturnSavedDto() {
        AllergenRequestDto request = new AllergenRequestDto("Peanuts");
        Allergen entity = Allergen.builder().id(1L).name("Peanuts").build();
        AllergenResponseDto responseDto = new AllergenResponseDto(1L, "Peanuts");

        when(allergenRequestMapper.toEntity(request)).thenReturn(entity);
        when(allergenRepository.save(entity)).thenReturn(entity);
        when(allergenResponseMapper.toDto(entity)).thenReturn(responseDto);

        AllergenResponseDto result = allergenService.create(request);

        assertThat(result).isEqualTo(responseDto);
        verify(allergenRepository).save(entity);
    }

    @Test
    void getAll_shouldReturnAllAllergens() {
        Allergen entity = Allergen.builder().id(1L).name("Peanuts").build();
        AllergenResponseDto responseDto = new AllergenResponseDto(1L, "Peanuts");

        when(allergenRepository.findAll()).thenReturn(List.of(entity));
        when(allergenResponseMapper.toDto(entity)).thenReturn(responseDto);

        List<AllergenResponseDto> result = allergenService.getAll();

        assertThat(result).containsExactly(responseDto);
    }

    @Test
    void getById_shouldReturnWhenFound() {
        Allergen entity = Allergen.builder().id(1L).name("Peanuts").build();
        AllergenResponseDto responseDto = new AllergenResponseDto(1L, "Peanuts");

        when(allergenRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(allergenResponseMapper.toDto(entity)).thenReturn(responseDto);

        AllergenResponseDto result = allergenService.getById(1L);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(allergenRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> allergenService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Allergen not found.");
    }

    @Test
    void delete_shouldCallRepository() {
        allergenService.delete(1L);

        verify(allergenRepository).deleteById(1L);
    }

    @Test
    void update_shouldUpdateAndReturnDto() {
        Allergen entity = Allergen.builder().id(1L).name("Peanuts").build();
        AllergenRequestDto request = new AllergenRequestDto("Tree Nuts");
        AllergenResponseDto responseDto = new AllergenResponseDto(1L, "Tree Nuts");

        when(allergenRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(allergenRepository.save(entity)).thenReturn(entity);
        when(allergenResponseMapper.toDto(entity)).thenReturn(responseDto);

        AllergenResponseDto result = allergenService.update(1L, request);

        assertThat(result).isEqualTo(responseDto);
        assertThat(entity.getName()).isEqualTo("Tree Nuts");
    }
}
