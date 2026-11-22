package org.spring.divas.venue.feature.category;

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
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, categoryRequestMapper, categoryResponseMapper);
    }

    @Test
    void create_shouldReturnSavedDto() {
        CategoryRequestDto request = new CategoryRequestDto(1L, "Desserts");
        Category entity = Category.builder().id(10L).venueId(1L).name("Desserts").build();
        CategoryResponseDto responseDto = new CategoryResponseDto(10L, 1L, "Desserts");

        when(categoryRequestMapper.toEntity(request)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(entity);
        when(categoryResponseMapper.toDto(entity)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.create(request);

        assertThat(result).isEqualTo(responseDto);
        verify(categoryRepository).save(entity);
    }

    @Test
    void getAll_shouldReturnAllCategories() {
        Category category = Category.builder().id(10L).venueId(1L).name("Desserts").build();
        CategoryResponseDto responseDto = new CategoryResponseDto(10L, 1L, "Desserts");

        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryResponseMapper.toDto(category)).thenReturn(responseDto);

        List<CategoryResponseDto> result = categoryService.getAll();

        assertThat(result).containsExactly(responseDto);
    }

    @Test
    void getById_shouldReturnWhenFound() {
        Category category = Category.builder().id(10L).venueId(1L).name("Desserts").build();
        CategoryResponseDto responseDto = new CategoryResponseDto(10L, 1L, "Desserts");

        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(categoryResponseMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.getById(10L);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category not found.");
    }

    @Test
    void delete_shouldCallRepository() {
        categoryService.delete(10L);

        verify(categoryRepository).deleteById(10L);
    }

    @Test
    void update_shouldUpdateFieldsAndReturnDto() {
        Category category = Category.builder().id(10L).venueId(1L).name("Old Name").build();
        CategoryRequestDto updateDto = new CategoryRequestDto(2L, "New Name");
        CategoryResponseDto responseDto = new CategoryResponseDto(10L, 2L, "New Name");

        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryResponseMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.update(10L, updateDto);

        assertThat(result).isEqualTo(responseDto);
        assertThat(category.getVenueId()).isEqualTo(2L);
        assertThat(category.getName()).isEqualTo("New Name");
    }

    @Test
    void update_shouldThrowWhenNotFound() {
        CategoryRequestDto updateDto = new CategoryRequestDto(2L, "New Name");
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.update(99L, updateDto))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
