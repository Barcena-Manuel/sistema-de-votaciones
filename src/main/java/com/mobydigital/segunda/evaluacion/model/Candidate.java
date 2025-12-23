package com.mobydigital.segunda.evaluacion.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Candidate {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "political_party_id",nullable = false)
    private PoliticalParty politicalParty;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();
}
