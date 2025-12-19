package com.mobydigital.segunda.evaluacion.repository;

import com.mobydigital.segunda.evaluacion.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
