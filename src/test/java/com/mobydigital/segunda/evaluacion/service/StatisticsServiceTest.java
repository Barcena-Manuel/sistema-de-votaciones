package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.VotesByCandidateDto;
import com.mobydigital.segunda.evaluacion.dto.VotesByPoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void getVotesByCandidate_shouldReturnListOfDtos() {
        VotesByCandidateDto votesByCandidateDto = new VotesByCandidateDto(1L,"Candidato Test", 10L);
        List<VotesByCandidateDto> expectedList = List.of(votesByCandidateDto);

        when(voteRepository.countVotesByCandidate()).thenReturn(expectedList);

        List<VotesByCandidateDto> actualList = statisticsService.getVotesByCandidate();

        assertFalse(actualList.isEmpty());
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getCandidateName(), actualList.get(0).getCandidateName());
        assertEquals(expectedList.get(0).getTotalVotes(), actualList.get(0).getTotalVotes());
    }

    @Test
    void getVotesByCandidate_shouldReturnEmptyList_whenNoVotes() {
        when(voteRepository.countVotesByCandidate()).thenReturn(Collections.emptyList());

        List<VotesByCandidateDto> result = statisticsService.getVotesByCandidate();

        assertTrue(result.isEmpty());
    }

    @Test
    void getVotesByPoliticalParty_shouldReturnListOfDtos() {
        VotesByPoliticalPartyDto votesByPartyDto = new VotesByPoliticalPartyDto(1L, "Partido Test", 20L);
        List<VotesByPoliticalPartyDto> expectedList = List.of(votesByPartyDto);

        when(voteRepository.countVotesByPoliticalParty()).thenReturn(expectedList);

        List<VotesByPoliticalPartyDto> actualList = statisticsService.getVotesByPoliticalParty();

        assertFalse(actualList.isEmpty());
        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getPoliticalPartyName(), actualList.get(0).getPoliticalPartyName());
        assertEquals(expectedList.get(0).getTotalVotes(), actualList.get(0).getTotalVotes());
    }

    @Test
    void getVotesByPoliticalParty_shouldReturnEmptyList_whenNoVotes() {
        when(voteRepository.countVotesByPoliticalParty()).thenReturn(Collections.emptyList());

        List<VotesByPoliticalPartyDto> result = statisticsService.getVotesByPoliticalParty();

        assertTrue(result.isEmpty());
    }
}
