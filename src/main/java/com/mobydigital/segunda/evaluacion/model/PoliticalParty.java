package com.mobydigital.segunda.evaluacion.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PoliticalParty {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String initials;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "politicalParty")
    private List<Candidate> candidates = new ArrayList<>();
}
