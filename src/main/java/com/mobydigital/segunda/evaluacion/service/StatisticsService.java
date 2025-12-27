package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.VotesByCandidateDto;
import com.mobydigital.segunda.evaluacion.dto.VotesByPoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final VoteRepository voteRepository;

    public StatisticsService(VoteRepository repository){
        this.voteRepository = repository;
    }

    public List<VotesByCandidateDto> getVotesByCandidate(){
        return voteRepository.countVotesByCandidate();
    }

    public List<VotesByPoliticalPartyDto> getVotesByPoliticalParty(){
        return voteRepository.countVotesByPoliticalParty();
    }
}
