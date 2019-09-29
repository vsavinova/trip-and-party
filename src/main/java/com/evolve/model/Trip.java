package com.evolve.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int id;
    @Column(name = "org_id")
    private int orgId;
    @ManyToOne
    @JoinColumn(name = "guide_id")
//    @JsonBackReference
    private Guide guide;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "visibility")
    private Visibility visibility;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "chat")
    private String chat;
    @Column(name = "finish_date")
    private LocalDate finishDate;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private Collection<TripParticipant> participants;
    @Transient
    private Collection<TripParticipant> friendParticipants = new ArrayList<>();
}
