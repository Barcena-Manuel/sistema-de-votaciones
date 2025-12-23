package com.mobydigital.segunda.evaluacion.service.mapper;

import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface VoteMapper {

    @Mapping(source = "candidate", target = "candidateDto")
    VoteDto toDto(Vote vote);

    @Mapping(source = "candidateDto", target = "candidate")
    Vote toEntity(VoteDto voteDto);
}