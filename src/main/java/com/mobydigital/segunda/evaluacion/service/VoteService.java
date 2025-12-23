package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.exception.CandidateNotExistException;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import com.mobydigital.segunda.evaluacion.model.Vote;
import com.mobydigital.segunda.evaluacion.repository.CandidateRepository;
import com.mobydigital.segunda.evaluacion.repository.VoteRepository;
import com.mobydigital.segunda.evaluacion.service.mapper.VoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRepository repository;
    private final CandidateRepository candidateRepository;
    private final VoteMapper voteMapper;

    @Autowired
    public VoteService(VoteRepository repository, CandidateRepository candidateRepository, VoteMapper voteMapper) {
        this.repository = repository;
        this.candidateRepository = candidateRepository;
        this.voteMapper = voteMapper;
    }

    public VoteDto create(VoteDto voteDto) throws InvalidDataException, CandidateNotExistException {

        if (voteDto.getCandidateId() == null) {
            throw new InvalidDataException();
        }

        Candidate candidate = candidateRepository
                .findById(voteDto.getCandidateId())
                .orElseThrow(() -> new CandidateNotExistException(voteDto.getCandidateId()));

        Vote vote = new Vote();
        vote.setCandidate(candidate);
        vote.setDate(voteDto.getDate() != null ? voteDto.getDate() : LocalDateTime.now());

        Vote saved = repository.save(vote);

        return voteMapper.toDto(saved);
    }

    public List<VoteDto> getAll() {
        return repository.findAll().stream().map(voteMapper::toDto).toList();
    }

}