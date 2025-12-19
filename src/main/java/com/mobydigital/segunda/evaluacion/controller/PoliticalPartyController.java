package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import com.mobydigital.segunda.evaluacion.service.CandidateService;
import com.mobydigital.segunda.evaluacion.service.PoliticalPartyService;
import org.hibernate.engine.spi.Resolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/politicalParty")
public class PoliticalPartyController {

    private PoliticalPartyService service;

    @Autowired
    public PoliticalPartyController(PoliticalPartyService service){
        this.service = service;
    }

    @PostMapping("/init")
    public ResponseEntity<List<PoliticalParty>> init(@RequestBody List<PoliticalParty> politicalParty) throws PoliticalPartyNotFoundException {
        return new ResponseEntity<>(service.init(politicalParty),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PoliticalParty>> getAllPoliticalsParties(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliticalParty> getPoliticalPartyById(@PathVariable Long id) throws PoliticalPartyNotFoundException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoliticalPartyById(@PathVariable Long id) throws InvalidDataException {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
