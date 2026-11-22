package org.spring.divas.venue.feature.venuetable;

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
class VenueTableServiceImplTest {

    @Mock
    private VenueTableRepository venueTableRepository;

    @Mock
    private VenueTableResponseMapper venueTableResponseMapper;

    @Mock
    private VenueTableRequestMapper venueTableRequestMapper;

    private VenueTableServiceImpl venueTableService;

    @BeforeEach
    void setUp() {
        venueTableService = new VenueTableServiceImpl(venueTableRepository, venueTableResponseMapper, venueTableRequestMapper);
    }

    @Test
    void create_shouldReturnSavedDto() {
        VenueTableRequestDto request = new VenueTableRequestDto(1L, true);
        VenueTable table = VenueTable.builder().id(5L).venueId(1L).isAvailable(true).build();
        VenueTableResponseDto responseDto = new VenueTableResponseDto(5L, 1L, true);

        when(venueTableRequestMapper.toEntity(request)).thenReturn(table);
        when(venueTableRepository.save(table)).thenReturn(table);
        when(venueTableResponseMapper.toDto(table)).thenReturn(responseDto);

        VenueTableResponseDto result = venueTableService.create(request);

        assertThat(result).isEqualTo(responseDto);
        verify(venueTableRepository).save(table);
    }

    @Test
    void getAll_shouldReturnTables() {
        VenueTable table = VenueTable.builder().id(5L).venueId(1L).isAvailable(true).build();
        VenueTableResponseDto responseDto = new VenueTableResponseDto(5L, 1L, true);

        when(venueTableRepository.findAll()).thenReturn(List.of(table));
        when(venueTableResponseMapper.toDto(table)).thenReturn(responseDto);

        List<VenueTableResponseDto> result = venueTableService.getAll();

        assertThat(result).containsExactly(responseDto);
    }

    @Test
    void getById_shouldReturnWhenFound() {
        VenueTable table = VenueTable.builder().id(5L).venueId(1L).isAvailable(true).build();
        VenueTableResponseDto responseDto = new VenueTableResponseDto(5L, 1L, true);

        when(venueTableRepository.findById(5L)).thenReturn(Optional.of(table));
        when(venueTableResponseMapper.toDto(table)).thenReturn(responseDto);

        VenueTableResponseDto result = venueTableService.getById(5L);

        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void getById_shouldThrowWhenNotFound() {
        when(venueTableRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> venueTableService.getById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Table not found.");
    }

    @Test
    void delete_shouldCallRepository() {
        venueTableService.delete(5L);

        verify(venueTableRepository).deleteById(5L);
    }

    @Test
    void update_shouldUpdateFieldsAndReturnDto() {
        VenueTable table = VenueTable.builder().id(5L).venueId(1L).isAvailable(true).build();
        VenueTableRequestDto updateDto = new VenueTableRequestDto(1L, false);
        VenueTableResponseDto responseDto = new VenueTableResponseDto(5L, 1L, false);

        when(venueTableRepository.findById(5L)).thenReturn(Optional.of(table));
        when(venueTableRepository.save(table)).thenReturn(table);
        when(venueTableResponseMapper.toDto(table)).thenReturn(responseDto);

        VenueTableResponseDto result = venueTableService.update(5L, updateDto);

        assertThat(result).isEqualTo(responseDto);
        assertThat(table.getIsAvailable()).isFalse();
    }
}
