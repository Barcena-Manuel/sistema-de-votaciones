package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.CandidateDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import com.mobydigital.segunda.evaluacion.repository.CandidateRepository;
import com.mobydigital.segunda.evaluacion.repository.PoliticalPartyRepository;
import com.mobydigital.segunda.evaluacion.mapper.CandidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepository repository;
    private final CandidateMapper candidateMapper;
    private final PoliticalPartyRepository politicalPartyRepository;

    @Autowired
    public CandidateService(CandidateRepository repository, CandidateMapper candidateMapper, PoliticalPartyRepository politicalPartyRepository) {
        this.repository = repository;
        this.candidateMapper = candidateMapper;
        this.politicalPartyRepository = politicalPartyRepository;
    }

    @Transactional
    public CandidateDto create(CandidateDto candidateDto) throws InvalidDataException {
        if (candidateDto.getPoliticalPartyDto() == null || candidateDto.getPoliticalPartyDto().getId() == null) {
            throw new InvalidDataException();
        }
        
        Candidate candidate = candidateMapper.toEntity(candidateDto);

        PoliticalParty party = politicalPartyRepository
                .findById(candidateDto.getPoliticalPartyDto().getId())
                .orElseThrow(InvalidDataException::new);

        candidate.setPoliticalParty(party);

        return candidateMapper.toDto(repository.save(candidate));
    }

    @Transactional(readOnly = true)
    public List<CandidateDto> getAll() {
        return repository.findAll().stream()
                .map(candidateMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CandidateDto getById(Long id) throws CandidateNotExistException {
        Candidate candidate = repository.findById(id).orElseThrow(() -> new CandidateNotExistException(id));
        return candidateMapper.toDto(candidate);
    }

    public void deleteById(Long id) throws CandidateNotExistException {
        if (!exists(id)) {
            throw new CandidateNotExistException(id);
        }

        repository.deleteById(id);
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}

