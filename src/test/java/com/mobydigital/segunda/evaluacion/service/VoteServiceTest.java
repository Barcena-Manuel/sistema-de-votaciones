package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.mapper.VoteMapper;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import com.mobydigital.segunda.evaluacion.model.Vote;
import com.mobydigital.segunda.evaluacion.repository.CandidateRepository;
import com.mobydigital.segunda.evaluacion.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository repository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private VoteMapper voteMapper;

    @InjectMocks
    private VoteService service;

    private Vote vote;
    private VoteDto voteDto;
    private Candidate candidate;

    @BeforeEach
    void setUp() {
        candidate = new Candidate(1L, "Candidato Test", null, null);
        voteDto = new VoteDto(1L, 1L, LocalDateTime.now());
        vote = new Vote(1L, candidate, voteDto.getDate());
    }

    @Test
    void create_shouldThrowInvalidDataException_whenCandidateIdIsNull() {
        voteDto.setCandidateId(null);
        assertThrows(InvalidDataException.class, () -> service.create(voteDto));
    }

    @Test
    void create_shouldThrowCandidateNotExistException_whenCandidateNotFound() {
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CandidateNotExistException.class, () -> service.create(voteDto));
    }

    @Test
    void create_shouldSaveVoteWithCurrentDate_whenDateIsNullInDto() throws InvalidDataException, CandidateNotExistException {
        voteDto.setDate(null);
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(repository.save(any(Vote.class))).thenReturn(vote);
        when(voteMapper.toDto(any(Vote.class))).thenReturn(voteDto);

        service.create(voteDto);

        ArgumentCaptor<Vote> voteCaptor = ArgumentCaptor.forClass(Vote.class);
        verify(repository).save(voteCaptor.capture());

        assertNotNull(voteCaptor.getValue().getDate());
    }

    @Test
    void create_shouldSaveVoteWithProvidedDate_whenDateIsNotNullInDto() throws InvalidDataException, CandidateNotExistException {
        LocalDateTime specificDate = LocalDateTime.of(2023, 1, 1, 10, 0);
        voteDto.setDate(specificDate);

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(repository.save(any(Vote.class))).thenReturn(vote);
        when(voteMapper.toDto(any(Vote.class))).thenReturn(voteDto);

        service.create(voteDto);

        ArgumentCaptor<Vote> voteCaptor = ArgumentCaptor.forClass(Vote.class);
        verify(repository).save(voteCaptor.capture());

        assertEquals(specificDate, voteCaptor.getValue().getDate());
    }

    @Test
    void getAll_shouldReturnListOfDtos() {
        when(repository.findAll()).thenReturn(List.of(vote));
        when(voteMapper.toDto(any(Vote.class))).thenReturn(voteDto);

        List<VoteDto> result = service.getAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(voteDto.getId(), result.get(0).getId());
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoVotes() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<VoteDto> result = service.getAll();

        assertTrue(result.isEmpty());
    }
}
