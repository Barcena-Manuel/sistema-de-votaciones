package com.mobydigital.segunda.evaluacion.runner;

import com.mobydigital.segunda.evaluacion.dto.CandidateDto;
import com.mobydigital.segunda.evaluacion.dto.PoliticalPartyDto;
import com.mobydigital.segunda.evaluacion.dto.VoteDto;
import com.mobydigital.segunda.evaluacion.service.CandidateService;
import com.mobydigital.segunda.evaluacion.service.PoliticalPartyService;
import com.mobydigital.segunda.evaluacion.service.VoteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Configuration
public class RunCommand {

    private static final Logger logger = LoggerFactory.getLogger(RunCommand.class);

    @Bean
    CommandLineRunner initData(CandidateService candidateService, PoliticalPartyService politicalPartyService, VoteService voteService){
        return args -> {

            logger.info("Loading initial data");

            PoliticalPartyDto party1 = politicalPartyService.create(new PoliticalPartyDto(null, "Partido Rojo", "PR"));
            PoliticalPartyDto party2 = politicalPartyService.create(new PoliticalPartyDto(null, "Partido Amarillo", "PA"));
            PoliticalPartyDto party3 = politicalPartyService.create(new PoliticalPartyDto(null, "Partido Verde", "PV"));

            CandidateDto candidate1 = new CandidateDto();
            candidate1.setFullName("Gero Arias");
            candidate1.setPoliticalPartyDto(party2);

            CandidateDto candidate2 = new CandidateDto();
            candidate2.setFullName("Tomas Mazza");
            candidate2.setPoliticalPartyDto(party1);

            CandidateDto candidate3 = new CandidateDto();
            candidate3.setFullName("Martin Perez Disalvo");
            candidate3.setPoliticalPartyDto(party3);

            candidate1 = candidateService.create(candidate1);
            candidate2 = candidateService.create(candidate2);
            candidate3 = candidateService.create(candidate3);

            voteService.create(new VoteDto(null, candidate1.getId(), LocalDateTime.now()));
            voteService.create(new VoteDto(null, candidate1.getId(), LocalDateTime.now()));
            voteService.create(new VoteDto(null, candidate2.getId(), LocalDateTime.now()));
            voteService.create(new VoteDto(null, candidate2.getId(), LocalDateTime.now()));
            voteService.create(new VoteDto(null, candidate3.getId(), LocalDateTime.now()));
            voteService.create(new VoteDto(null, candidate3.getId(), LocalDateTime.now()));


            logger.info("Initial data loaded");
        };
    }
}
