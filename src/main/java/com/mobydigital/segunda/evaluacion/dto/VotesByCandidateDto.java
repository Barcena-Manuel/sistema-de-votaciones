package com.mobydigital.segunda.evaluacion.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VotesByCandidateDto {

    private Long candidateId;

    private String candidateName;

    private Long totalVotes;

}
