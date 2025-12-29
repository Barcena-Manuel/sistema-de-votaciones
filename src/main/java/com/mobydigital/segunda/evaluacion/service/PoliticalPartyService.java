package com.mobydigital.segunda.evaluacion.service;

import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.exception.InvalidDataException;
import com.mobydigital.segunda.evaluacion.exception.PoliticalPartyNotFoundException;
import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import com.mobydigital.segunda.evaluacion.repository.PoliticalPartyRepository;
import com.mobydigital.segunda.evaluacion.mapper.PoliticalPartyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PoliticalPartyService {

    private final PoliticalPartyRepository repository;
    private final PoliticalPartyMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(PoliticalPartyService.class);

    @Autowired
    public PoliticalPartyService(PoliticalPartyRepository repository, PoliticalPartyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public PoliticalPartyDto create(PoliticalPartyDto partyDto) throws InvalidDataException {
        if (partyDto.getName() == null || partyDto.getInitials() == null) {
            logger.info("Invalid data");
            throw new InvalidDataException();
        }

        logger.info("Creating political party");
        PoliticalParty party = mapper.toEntity(partyDto);
        return mapper.toDto(repository.save(party));
    }


    @Transactional(readOnly = true)
    public List<PoliticalPartyDto> getAll() {
        logger.info("Getting all politicals parties");
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public PoliticalPartyDto getById(Long id) throws PoliticalPartyNotFoundException {
        logger.info("Getting by id");
        PoliticalParty party = repository.findById(id).orElseThrow(() -> new PoliticalPartyNotFoundException(id));

        return mapper.toDto(party);
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    public void deleteById(Long id) throws PoliticalPartyNotFoundException {
        if (!exists(id)) {
            logger.info("Political party doesn't exist");
            throw new PoliticalPartyNotFoundException(id);
        }

        logger.info("Political party deleted");
        repository.deleteById(id);
    }
}
