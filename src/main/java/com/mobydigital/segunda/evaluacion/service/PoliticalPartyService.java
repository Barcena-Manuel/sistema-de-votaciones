package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import com.mobydigital.segunda.evaluacion.repository.PoliticalPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticalPartyService {

    private PoliticalPartyRepository repository;

    @Autowired
    public PoliticalPartyService(PoliticalPartyRepository repository){
        this.repository = repository;
    }

    public List<PoliticalParty> init(List<PoliticalParty> politicalParty) throws PoliticalPartyNotFoundException{
        if(politicalParty.isEmpty()){
            throw new PoliticalPartyNotFoundException();
        }
        return repository.saveAll(politicalParty);
    }

    public List<PoliticalParty> getAll(){
        return repository.findAll();
    }

    public PoliticalParty getById(Long id) throws PoliticalPartyNotFoundException{
        return repository.findById(id).orElseThrow(() -> new PoliticalPartyNotFoundException());
    }

    public boolean exists(Long id){
        return repository.existsById(id);
    }


    public void deleteById(Long id) throws InvalidDataException {
        if(!exists(id)){
            throw new InvalidDataException();
        }

        repository.deleteById(id);
    }
}
