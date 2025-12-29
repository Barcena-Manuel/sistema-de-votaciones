package com.mobydigital.segunda.evaluacion.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CandidateDto {

    private Long id;

    private String fullName;

    private PoliticalPartyDto politicalPartyDto;

    private List<VoteDto> votesDto = new ArrayList<>();
}
