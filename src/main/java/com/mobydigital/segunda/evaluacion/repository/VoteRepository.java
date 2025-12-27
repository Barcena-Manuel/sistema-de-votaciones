package com.mobydigital.segunda.evaluacion.repository;

import com.mobydigital.segunda.evaluacion.dto.VotesByCandidateDto;
import com.mobydigital.segunda.evaluacion.dto.VotesByPoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("""
        SELECT new com.mobydigital.segunda.evaluacion.dto.VotesByCandidateDto(
            c.id,
            c.fullName,
            COUNT(v.id)
        )
        FROM Vote v
        JOIN v.candidate c
        GROUP BY c.id, c.fullName
    """)
    List<VotesByCandidateDto> countVotesByCandidate();


    @Query("""
        SELECT new com.mobydigital.segunda.evaluacion.dto.VotesByPoliticalPartyDto(
            p.id,
            p.name,
            COUNT(v.id)
        )
        FROM Vote v
        JOIN v.candidate c
        JOIN c.politicalParty p
        GROUP BY p.id, p.name
    """)
    List<VotesByPoliticalPartyDto> countVotesByPoliticalParty();
}
