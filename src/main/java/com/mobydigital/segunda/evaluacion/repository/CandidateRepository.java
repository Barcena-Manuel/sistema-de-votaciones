package com.mobydigital.segunda.evaluacion.repository;

import com.mobydigital.segunda.evaluacion.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}
