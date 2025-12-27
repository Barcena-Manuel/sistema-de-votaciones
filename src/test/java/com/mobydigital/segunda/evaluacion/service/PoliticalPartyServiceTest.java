package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.mapper.PoliticalPartyMapper;
import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import com.mobydigital.segunda.evaluacion.repository.PoliticalPartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoliticalPartyServiceTest {

    @Mock
    private PoliticalPartyRepository repository;

    @Mock
    private PoliticalPartyMapper mapper;

    @InjectMocks
    private PoliticalPartyService service;

    private PoliticalParty politicalParty;
    private PoliticalPartyDto politicalPartyDto;

    @BeforeEach
    void setUp() {
        politicalParty = new PoliticalParty(1L, "Partido Test", "PT", Collections.emptyList());
        politicalPartyDto = new PoliticalPartyDto(1L, "Partido Test", "PT");
    }

    @Test
    void create_shouldThrowInvalidDataException_whenNameIsNull() {
        politicalPartyDto.setName(null);
        assertThrows(InvalidDataException.class, () -> service.create(politicalPartyDto));
    }

    @Test
    void create_shouldThrowInvalidDataException_whenInitialsAreNull() {
        politicalPartyDto.setInitials(null);
        assertThrows(InvalidDataException.class, () -> service.create(politicalPartyDto));
    }

    @Test
    void create_shouldReturnSavedDto_whenDataIsValid() throws InvalidDataException {
        when(mapper.toEntity(any(PoliticalPartyDto.class))).thenReturn(politicalParty);
        when(repository.save(any(PoliticalParty.class))).thenReturn(politicalParty);
        when(mapper.toDto(any(PoliticalParty.class))).thenReturn(politicalPartyDto);

        PoliticalPartyDto result = service.create(politicalPartyDto);

        assertNotNull(result);
        assertEquals(politicalPartyDto.getId(), result.getId());
        verify(repository, times(1)).save(politicalParty);
    }

    @Test
    void getAll_shouldReturnListOfDtos() {
        when(repository.findAll()).thenReturn(List.of(politicalParty));
        when(mapper.toDto(any(PoliticalParty.class))).thenReturn(politicalPartyDto);

        List<PoliticalPartyDto> result = service.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(politicalPartyDto.getName(), result.get(0).getName());
    }
    
    @Test
    void getAll_shouldReturnEmptyList_whenNoParties() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
    
        List<PoliticalPartyDto> result = service.getAll();
    
        assertTrue(result.isEmpty());
    }

    @Test
    void getById_shouldReturnDto_whenFound() throws PoliticalPartyNotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(politicalParty));
        when(mapper.toDto(any(PoliticalParty.class))).thenReturn(politicalPartyDto);

        PoliticalPartyDto result = service.getById(1L);

        assertNotNull(result);
        assertEquals(politicalPartyDto.getId(), result.getId());
    }

    @Test
    void getById_shouldThrowPoliticalPartyNotFoundException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PoliticalPartyNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void deleteById_shouldThrowPoliticalPartyNotFoundException_whenNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(PoliticalPartyNotFoundException.class, () -> service.deleteById(1L));
    }

    @Test
    void deleteById_shouldCallDeleteById_whenFound() throws PoliticalPartyNotFoundException {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
