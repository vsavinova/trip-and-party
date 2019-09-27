package com.evolve.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "tripparticipant")
public class TripParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private int id;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "accept_status")
    private AcceptStatus accept_status;
    @Column(name = "role")
    private String role;
    //    private Collection<Ticket> tickets; // maybe more than 1 ticket (msk->ln, ln->msk)
    @ManyToOne
    @JoinColumn(name = "trip_id")
    @JsonBackReference
    private Trip trip;
}
