package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.dto.VotesByCandidateDto;
import com.mobydigital.segunda.evaluacion.dto.VotesByPoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.service.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticsController.class)
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsService service;

    @Test
    void votesByCandidate_shouldReturn200Ok_withList() throws Exception {
        VotesByCandidateDto dto = new VotesByCandidateDto(1L, "Candidato Test", 100L);
        when(service.getVotesByCandidate()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/statistics/votes-by-candidate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].candidateName").value("Candidato Test"))
                .andExpect(jsonPath("$[0].totalVotes").value(100L));
    }

    @Test
    void votesByCandidate_shouldReturn200Ok_withEmptyList() throws Exception {
        when(service.getVotesByCandidate()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/statistics/votes-by-candidate"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void votesByPoliticalParty_shouldReturn200Ok_withList() throws Exception {
        VotesByPoliticalPartyDto dto = new VotesByPoliticalPartyDto(1L,"Partido Test", 200L);
        when(service.getVotesByPoliticalParty()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/statistics/votes-by-political-party"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].politicalPartyName").value("Partido Test"))
                .andExpect(jsonPath("$[0].totalVotes").value(200L));
    }

    @Test
    void votesByPoliticalParty_shouldReturn200Ok_withEmptyList() throws Exception {
        when(service.getVotesByPoliticalParty()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/statistics/votes-by-political-party"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
