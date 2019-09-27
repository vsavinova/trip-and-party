package com.evolve.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "hashtag")
@JsonIgnoreProperties(value = {"hashtag_id", "guides"},
        allowGetters = true)
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private int id;
    @Column(name = "name")
    private String name;
    //    @ManyToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER)
//    @JoinTable(name = "guide_hashtag",
//            joinColumns = { @JoinColumn(name = "hashtag_id") },
//            inverseJoinColumns = { @JoinColumn(name = "guide_id") })
//    @JsonBackReference
    @ManyToMany(mappedBy = "hashtags")
    private Collection<Guide> guides;
}
