package com.mobydigital.segunda.evaluacion.mapper;

import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PoliticalPartyMapper {

    PoliticalPartyDto toDto(PoliticalParty party);

    // Opcional (solo si lo necesitás para create)
    PoliticalParty toEntity(PoliticalPartyDto dto);
}
