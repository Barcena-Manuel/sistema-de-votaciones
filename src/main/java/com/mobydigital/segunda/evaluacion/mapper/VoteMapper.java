package com.mobydigital.segunda.evaluacion.mapper;

import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface VoteMapper {

    @Mapping(source = "candidate.id", target = "candidateId")
    VoteDto toDto(Vote vote);

    @Mapping(target = "candidate", ignore = true)
    Vote toEntity(VoteDto voteDto);
}