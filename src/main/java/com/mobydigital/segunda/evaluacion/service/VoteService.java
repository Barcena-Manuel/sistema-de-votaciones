package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.Vote;
import com.mobydigital.segunda.evaluacion.repository.CandidateRepository;
import com.mobydigital.segunda.evaluacion.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private VoteRepository repository;
    private CandidateService candidateService;
    private PoliticalPartyService politicalService;

    public List<Vote> init(List<Vote> voteList) throws InvalidDataException {
        if(voteList.isEmpty()){
            throw new InvalidDataException();
        }

        return repository.saveAll(voteList);
    }

    public Vote create(Vote vote) throws InvalidDataException, CandidateNotExistException, PoliticalPartyNotFoundException {

        Long idCandidate = vote.getCandidate().getId();
        Long idPoliticalParty = vote.getCandidate().getParty().getId();

        if(idCandidate == null || idPoliticalParty == null){
            throw new InvalidDataException();
        } else if(!candidateService.exist(idCandidate)) {
            throw new CandidateNotExistException(idCandidate);
        } else if(!politicalService.exists(idPoliticalParty)){
            throw new PoliticalPartyNotFoundException(idPoliticalParty);
        }

        return repository.save(vote);
    }

    public List<Vote> getAll() {
        return repository.findAll();
    }

/*
    public Long votesPerCandidate(Long candidateId) {
    }

    public Long votesPerPoliticalParty() {
    }

 */
}
