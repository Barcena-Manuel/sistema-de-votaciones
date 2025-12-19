package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import com.mobydigital.segunda.evaluacion.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private CandidateRepository repository;
    private PoliticalPartyService politicalPartyService;

    @Autowired
    public CandidateService(CandidateRepository repository, PoliticalPartyService politicalParty){
        this.repository = repository;
        this.politicalPartyService = politicalParty;
    }

    public List<Candidate> init(List<Candidate> candidateList) throws InvalidDataException{
        if(candidateList.isEmpty()){
            throw new InvalidDataException();
        }
        return repository.saveAll(candidateList);
    }

    public Candidate create(Candidate candidate) throws InvalidDataException, PoliticalPartyNotFoundException {
        Long politicalPartyId = candidate.getParty().getId();

        if(politicalPartyId == null){
            throw new InvalidDataException();
        } else if (!politicalPartyService.exists(politicalPartyId)) {
            throw new PoliticalPartyNotFoundException();
        }

        candidate.setParty(politicalPartyService.getById(politicalPartyId));

        return repository.save(candidate);
    }

    public List<Candidate> getAll(){
        return repository.findAll();
    }

    public Candidate getById(Long id) throws CandidateNotExistException {
        return repository.findById(id).orElseThrow(() -> new CandidateNotExistException());
    }

    public void deleteById(Long id) throws CandidateNotExistException{
        if(!exist(id)){
            throw new CandidateNotExistException();
        }

        repository.deleteById(id);
    }

    public boolean exist(Long id){
        return repository.existsById(id);
    }
}

