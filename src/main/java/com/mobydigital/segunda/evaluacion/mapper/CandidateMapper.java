package com.mobydigital.segunda.evaluacion.mapper;

import com.mobydigital.segunda.evaluacion.dto.CandidateDto;
import com.mobydigital.segunda.evaluacion.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PoliticalPartyMapper.class, VoteMapper.class})
public interface CandidateMapper {

    @Mapping(source = "politicalParty", target = "politicalPartyDto")
    CandidateDto toDto(Candidate candidate);

    @Mapping(source = "politicalPartyDto", target = "politicalParty")
    @Mapping(target = "votes", ignore = true)
    Candidate toEntity(CandidateDto dto);
}

