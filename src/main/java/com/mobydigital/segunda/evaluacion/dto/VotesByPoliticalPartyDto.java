package com.mobydigital.segunda.evaluacion.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VotesByPoliticalPartyDto {

    private Long politicalPartyId;

    private String politicalPartyName;

    private Long totalVotes;

}
