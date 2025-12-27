package com.mobydigital.segunda.evaluacion.controller;

import com.mobydigital.segunda.evaluacion.dto.VotesByCandidateDto;
import com.mobydigital.segunda.evaluacion.dto.VotesByPoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService service;

    public StatisticsController(StatisticsService service){
        this.service = service;
    }

    @GetMapping("/votes-by-candidate")
    public List<VotesByCandidateDto> votesByCandidate(){
        return service.getVotesByCandidate();
    }

    @GetMapping("/votes-by-political-party")
    public List<VotesByPoliticalPartyDto> votesByPoliticalParty(){
        return service.getVotesByPoliticalParty();
    }
}
