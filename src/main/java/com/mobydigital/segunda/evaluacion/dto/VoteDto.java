package com.mobydigital.segunda.evaluacion.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class VoteDto {

    private Long id;

    private Long candidateId;

    private LocalDateTime date;
}

