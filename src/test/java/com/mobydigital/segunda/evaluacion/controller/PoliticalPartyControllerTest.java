package com.mobydigital.segunda.evaluacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.service.PoliticalPartyService;
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

@WebMvcTest(PoliticalPartyController.class)
class PoliticalPartyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PoliticalPartyService service;

    @Autowired
    private ObjectMapper objectMapper;

    private PoliticalPartyDto politicalPartyDto;

    @BeforeEach
    void setUp() {
        politicalPartyDto = new PoliticalPartyDto(1L, "Partido Test", "PT");
    }

    @Test
    void createPoliticalParty_shouldReturn201Created_whenValid() throws Exception {
        when(service.create(any(PoliticalPartyDto.class))).thenReturn(politicalPartyDto);

        mockMvc.perform(post("/api/political-party")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(politicalPartyDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Partido Test"));
    }

    @Test
    void createPoliticalParty_shouldReturn400BadRequest_whenInvalidData() throws Exception {
        when(service.create(any(PoliticalPartyDto.class))).thenThrow(new InvalidDataException());

        mockMvc.perform(post("/api/political-party")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PoliticalPartyDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPoliticalsParties_shouldReturn200Ok_withList() throws Exception {
        when(service.getAll()).thenReturn(List.of(politicalPartyDto));

        mockMvc.perform(get("/api/political-party"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Partido Test"));
    }
    
    @Test
    void getAllPoliticalsParties_shouldReturn200Ok_withEmptyList() throws Exception {
        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/political-party"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getPoliticalPartyById_shouldReturn200Ok_whenFound() throws Exception {
        when(service.getById(1L)).thenReturn(politicalPartyDto);

        mockMvc.perform(get("/api/political-party/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getPoliticalPartyById_shouldReturn404NotFound_whenNotFound() throws Exception {
        when(service.getById(anyLong())).thenThrow(new PoliticalPartyNotFoundException(1L));

        mockMvc.perform(get("/api/political-party/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePoliticalPartyById_shouldReturn204NoContent_whenDeleted() throws Exception {
        doNothing().when(service).deleteById(1L);

        mockMvc.perform(delete("/api/political-party/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePoliticalPartyById_shouldReturn404NotFound_whenNotFound() throws Exception {
        doThrow(new PoliticalPartyNotFoundException(1L)).when(service).deleteById(anyLong());

        mockMvc.perform(delete("/api/political-party/1"))
                .andExpect(status().isNotFound());
    }
}
