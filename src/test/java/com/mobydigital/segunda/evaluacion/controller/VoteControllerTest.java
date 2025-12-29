package com.mobydigital.segunda.evaluacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService service;

    private ObjectMapper objectMapper;

    private VoteDto voteDto;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        voteDto = new VoteDto(1L, 1L, LocalDateTime.now());
    }

    @Test
    void createVote_shouldReturn201Created_whenValid() throws Exception {
        when(service.create(any(VoteDto.class))).thenReturn(voteDto);

        mockMvc.perform(post("/api/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createVote_shouldReturn400BadRequest_whenInvalidData() throws Exception {
        when(service.create(any(VoteDto.class))).thenThrow(new InvalidDataException());

        mockMvc.perform(post("/api/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new VoteDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createVote_shouldReturn404NotFound_whenCandidateNotFound() throws Exception {
        when(service.create(any(VoteDto.class))).thenThrow(new CandidateNotExistException(1L));

        mockMvc.perform(post("/api/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voteDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllVote_shouldReturn200Ok_withList() throws Exception {
        when(service.getAll()).thenReturn(List.of(voteDto));

        mockMvc.perform(get("/api/vote"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getAllVote_shouldReturn200Ok_withEmptyList() throws Exception {
        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/vote"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
