package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.CandidateDto;
import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.mapper.CandidateMapper;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import com.mobydigital.segunda.evaluacion.repository.CandidateRepository;
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
class CandidateServiceTest {

    @Mock
    private CandidateRepository repository;

    @Mock
    private CandidateMapper candidateMapper;

    @Mock
    private PoliticalPartyRepository politicalPartyRepository;

    @InjectMocks
    private CandidateService service;

    private Candidate candidate;
    private CandidateDto candidateDto;
    private PoliticalParty politicalParty;
    private PoliticalPartyDto politicalPartyDto;

    @BeforeEach
    void setUp() {
        politicalParty = new PoliticalParty(1L, "Partido Test", "PT", Collections.emptyList());
        politicalPartyDto = new PoliticalPartyDto(1L, "Partido Test", "PT");
        candidate = new Candidate(1L, "Candidato Test", politicalParty, null);
        candidateDto = new CandidateDto(1L, "Candidato Test", politicalPartyDto, Collections.emptyList());
    }

    @Test
    void create_shouldThrowInvalidDataException_whenPoliticalPartyDtoIsNull() {
        candidateDto.setPoliticalPartyDto(null);
        assertThrows(InvalidDataException.class, () -> service.create(candidateDto));
    }

    @Test
    void create_shouldThrowInvalidDataException_whenPoliticalPartyIdIsNull() {
        politicalPartyDto.setId(null);
        candidateDto.setPoliticalPartyDto(politicalPartyDto);
        assertThrows(InvalidDataException.class, () -> service.create(candidateDto));
    }
    
    @Test
    void create_shouldThrowInvalidDataException_whenPoliticalPartyNotFound() {
        when(politicalPartyRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InvalidDataException.class, () -> service.create(candidateDto));
    }

    @Test
    void create_shouldReturnSavedDto_whenDataIsValid() throws InvalidDataException {
        when(candidateMapper.toEntity(any(CandidateDto.class))).thenReturn(candidate);
        when(politicalPartyRepository.findById(1L)).thenReturn(Optional.of(politicalParty));
        when(repository.save(any(Candidate.class))).thenReturn(candidate);
        when(candidateMapper.toDto(any(Candidate.class))).thenReturn(candidateDto);

        CandidateDto result = service.create(candidateDto);

        assertNotNull(result);
        assertEquals(candidateDto.getId(), result.getId());
        verify(repository, times(1)).save(candidate);
    }

    @Test
    void getAll_shouldReturnListOfDtos() {
        when(repository.findAll()).thenReturn(List.of(candidate));
        when(candidateMapper.toDto(any(Candidate.class))).thenReturn(candidateDto);

        List<CandidateDto> result = service.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(candidateDto.getFullName(), result.get(0).getFullName());
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoCandidates() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<CandidateDto> result = service.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void getById_shouldReturnDto_whenFound() throws CandidateNotExistException {
        when(repository.findById(1L)).thenReturn(Optional.of(candidate));
        when(candidateMapper.toDto(any(Candidate.class))).thenReturn(candidateDto);

        CandidateDto result = service.getById(1L);

        assertNotNull(result);
        assertEquals(candidateDto.getId(), result.getId());
    }

    @Test
    void getById_shouldThrowCandidateNotExistException_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CandidateNotExistException.class, () -> service.getById(1L));
    }
    
    @Test
    void deleteById_shouldThrowCandidateNotExistException_whenNotFound() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(CandidateNotExistException.class, () -> service.deleteById(1L));
    }

    @Test
    void deleteById_shouldCallDeleteById_whenFound() throws CandidateNotExistException {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.deleteById(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
