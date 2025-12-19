package com.mobydigital.segunda.evaluacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
@Entity
public class Candidate {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "political_party_id",nullable = false)
    private PoliticalParty party;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();
}
