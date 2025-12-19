package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import com.mobydigital.segunda.evaluacion.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    private CandidateService service;

    @Autowired
    public CandidateController(CandidateService service){
        this.service = service;
    }

    @PostMapping("/init")
    public ResponseEntity<List<Candidate>> init(@RequestBody List<Candidate> candidates) throws InvalidDataException {
        return new ResponseEntity<>(service.init(candidates), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) throws InvalidDataException, PoliticalPartyNotFoundException {
        return new ResponseEntity<>(service.create(candidate), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getCandidates(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) throws CandidateNotExistException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) throws CandidateNotExistException{
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
