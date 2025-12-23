package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.service.PoliticalPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/politicalParty")
public class PoliticalPartyController {

    private PoliticalPartyService service;

    @Autowired
    public PoliticalPartyController(PoliticalPartyService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PoliticalPartyDto> createPoliticalParty(@RequestBody PoliticalPartyDto politicalParty) throws InvalidDataException {
        return new ResponseEntity<>(service.create(politicalParty), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PoliticalPartyDto>> getAllPoliticalsParties(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliticalPartyDto> getPoliticalPartyById(@PathVariable Long id) throws PoliticalPartyNotFoundException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoliticalPartyById(@PathVariable Long id) throws PoliticalPartyNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
