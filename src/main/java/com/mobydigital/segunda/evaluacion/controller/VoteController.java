package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.Vote;
import com.mobydigital.segunda.evaluacion.service.VoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    private VoteService service;

    @PostMapping("/init")
    public ResponseEntity<List<Vote>> init(@RequestBody List<Vote> voteList) throws InvalidDataException {
        return new ResponseEntity<>(service.init(voteList), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) throws CandidateNotExistException, PoliticalPartyNotFoundException, InvalidDataException {
        return new ResponseEntity<>(service.create(vote), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vote>> getAllVote(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/candidate")
    public ResponseEntity<Long> getVotesPerCandidate(@PathVariable Long candidateId){
        return new ResponseEntity<>(service.votesPerCandidate(candidateId), HttpStatus.OK);
    }

    @GetMapping("/political-party")
    public ResponseEntity<Long> getVotesPerPoliticalParty(){
        return new ResponseEntity<>(service.votesPerPoliticalParty(), HttpStatus.OK);
    }
}
