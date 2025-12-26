package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.dto.CandidateDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Candidate", description = "Operations for candidates")
@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    private CandidateService service;

    @Autowired
    public CandidateController(CandidateService service){
        this.service = service;
    }


    @Operation(summary = "Create candidate",
            description = "Creates and returns a candidate",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Candidate was created"),
                    @ApiResponse(responseCode = "400", description = "Invalid data is not accepted")
            }
    )
    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(@RequestBody CandidateDto candidate) throws InvalidDataException {
        return new ResponseEntity<>(service.create(candidate), HttpStatus.CREATED);
    }


    @Operation(summary = "Getting candidates",
            description = "Obtains a list of all candidates",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All candidates were obtained"),
            }
    )
    @GetMapping
    public ResponseEntity<List<CandidateDto>> getCandidates(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


    @Operation(summary = "Getting candidates by ID",
            description = "Obtains a candidate by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Candidate was found"),
                    @ApiResponse(responseCode = "404", description = "The candidate was not found due to an invalid ID")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidateById(@PathVariable Long id) throws CandidateNotExistException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }


    @Operation(summary = "Delete candidate by id",
            description = "Delete the candidate by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted candidate"),
                    @ApiResponse(responseCode = "404", description = "The candidate was not removed due to an invalid ID")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) throws CandidateNotExistException{
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
