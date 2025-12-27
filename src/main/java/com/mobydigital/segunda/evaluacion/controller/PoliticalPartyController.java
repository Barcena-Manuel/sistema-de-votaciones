package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.service.PoliticalPartyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Political Party", description = "Operations for political party")
@RestController
@RequestMapping("/api/political-party")
public class PoliticalPartyController {

    private PoliticalPartyService service;

    @Autowired
    public PoliticalPartyController(PoliticalPartyService service){
        this.service = service;
    }


    @Operation(summary = "Create political party",
            description = "Creates and returns a political party",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Political Party was created"),
                    @ApiResponse(responseCode = "400", description = "Invalid data is not accepted")
            }
    )
    @PostMapping
    public ResponseEntity<PoliticalPartyDto> createPoliticalParty(@RequestBody PoliticalPartyDto politicalParty) throws InvalidDataException {
        return new ResponseEntity<>(service.create(politicalParty), HttpStatus.CREATED);
    }


    @Operation(summary = "Getting political party",
            description = "Obtains a list of all politicals parties",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All political parties were obtained"),
            }
    )
    @GetMapping
    public ResponseEntity<List<PoliticalPartyDto>> getAllPoliticalsParties(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }


    @Operation(summary = "Getting political party by ID",
            description = "Obtains a political party by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Political Party was found"),
                    @ApiResponse(responseCode = "404", description = "The political party was not found due to an invalid ID")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PoliticalPartyDto> getPoliticalPartyById(@PathVariable Long id) throws PoliticalPartyNotFoundException {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }


    @Operation(summary = "Delete political party by id",
            description = "Delete the political party by ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Deleted political party"),
                    @ApiResponse(responseCode = "404", description = "The political party was not removed due to an invalid ID")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoliticalPartyById(@PathVariable Long id) throws PoliticalPartyNotFoundException {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
