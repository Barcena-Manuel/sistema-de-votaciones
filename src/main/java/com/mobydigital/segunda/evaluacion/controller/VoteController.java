package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Vote", description = "Operations for votes")
@RestController
@RequestMapping("/api/vote")
public class VoteController {

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }


    @Operation(summary = "Create votes",
            description = "Creates and returns votes",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Vote was created"),
                    @ApiResponse(responseCode = "400", description = "Invalid data is not accepted"),
                    @ApiResponse(responseCode = "404", description = "The votes was not found due to an invalid ID")
            }
    )
    @PostMapping
    public ResponseEntity<VoteDto> createVote(@RequestBody VoteDto vote) throws CandidateNotExistException, InvalidDataException {
        return new ResponseEntity<>(service.create(vote), HttpStatus.CREATED);
    }


    @Operation(summary = "Getting votes",
            description = "Obtains a list of all votes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All votes were obtained"),
            }
    )
    @GetMapping
    public ResponseEntity<List<VoteDto>> getAllVote() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
