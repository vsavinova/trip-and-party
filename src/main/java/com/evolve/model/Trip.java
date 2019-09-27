package com.evolve.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter @Setter
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int id;
    @Column(name = "org_id")
    private int orgId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "visibility")
    private Visibility visibility;
    @Column(name = "startDate")
    private LocalDate startDate;
    @Column(name = "location")
    private String location;
    @Column(name = "chat")
    private String chat;
    @Column(name = "finishDate")
    private LocalDate finishDate;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private Collection<TripParticipant> participants;
    @Transient
    private Collection<TripParticipant> friendParticipants = new ArrayList<>();
}
