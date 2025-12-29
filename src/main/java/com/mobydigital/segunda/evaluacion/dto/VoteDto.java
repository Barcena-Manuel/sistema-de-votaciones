package com.mobydigital.segunda.evaluacion.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteDto {

    private Long id;

    private Long candidateId;

    private LocalDateTime date;
}

