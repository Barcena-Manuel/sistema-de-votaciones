package com.mobydigital.segunda.evaluacion.repository;

import com.mobydigital.segunda.evaluacion.model.PoliticalParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoliticalPartyRepository extends JpaRepository<PoliticalParty, Long> {
}
