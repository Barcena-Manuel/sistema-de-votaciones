package com.mobydigital.segunda.evaluacion.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CandidateDto {

    private Long id;

    private String fullName;

    private PoliticalPartyDto partyDto;

    private List<VoteDto> votesDto = new ArrayList<>();
}
