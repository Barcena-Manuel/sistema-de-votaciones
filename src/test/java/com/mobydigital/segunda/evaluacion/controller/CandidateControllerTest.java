package com.mobydigital.segunda.evaluacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobydigital.segunda.evaluacion.dto.CandidateDto;
import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.service.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService service;

    @Autowired
    private ObjectMapper objectMapper;

    private CandidateDto candidateDto;
    private PoliticalPartyDto politicalPartyDto;

    @BeforeEach
    void setUp() {
        politicalPartyDto = new PoliticalPartyDto(1L, "Partido Test", "PT");
        candidateDto = new CandidateDto(1L, "Candidato Test", politicalPartyDto, Collections.emptyList());
    }

    @Test
    void createCandidate_shouldReturn201Created_whenValid() throws Exception {
        when(service.create(any(CandidateDto.class))).thenReturn(candidateDto);

        mockMvc.perform(post("/api/candidate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Candidato Test"));
    }

    @Test
    void createCandidate_shouldReturn400BadRequest_whenInvalidData() throws Exception {
        when(service.create(any(CandidateDto.class))).thenThrow(new InvalidDataException());

        mockMvc.perform(post("/api/candidate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CandidateDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCandidates_shouldReturn200Ok_withList() throws Exception {
        when(service.getAll()).thenReturn(List.of(candidateDto));

        mockMvc.perform(get("/api/candidate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Candidato Test"));
    }

    @Test
    void getCandidates_shouldReturn200Ok_withEmptyList() throws Exception {
        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/candidate"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getCandidateById_shouldReturn200Ok_whenFound() throws Exception {
        when(service.getById(1L)).thenReturn(candidateDto);

        mockMvc.perform(get("/api/candidate/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getCandidateById_shouldReturn404NotFound_whenNotFound() throws Exception {
        when(service.getById(anyLong())).thenThrow(new CandidateNotExistException(1L));

        mockMvc.perform(get("/api/candidate/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCandidate_shouldReturn204NoContent_whenDeleted() throws Exception {
        doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/api/candidate/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCandidate_shouldReturn404NotFound_whenNotFound() throws Exception {
        doThrow(new CandidateNotExistException(1L)).when(service).deleteById(anyLong());

        mockMvc.perform(delete("/api/candidate/1"))
                .andExpect(status().isNotFound());
    }
}
