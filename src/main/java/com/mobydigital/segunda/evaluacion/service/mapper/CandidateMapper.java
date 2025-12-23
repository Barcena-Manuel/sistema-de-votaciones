package com.mobydigital.segunda.evaluacion.service.mapper;

import com.mobydigital.segunda.evaluacion.dto.CandidateDto;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PoliticalPartyMapper.class, VoteMapper.class})
public interface CandidateMapper {

    @Mapping(source = "politicalParty", target = "party")
    @Mapping(source = "votes", target = "votes")
    CandidateDto toDto(Candidate candidate);

    @Mapping(source = "party", target = "politicalParty")
    Candidate toEntity(CandidateDto dto);
}

