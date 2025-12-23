package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VoteDto> createVote(@RequestBody VoteDto vote) throws CandidateNotExistException, PoliticalPartyNotFoundException, InvalidDataException {
        return new ResponseEntity<>(service.create(vote), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VoteDto>> getAllVote() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
