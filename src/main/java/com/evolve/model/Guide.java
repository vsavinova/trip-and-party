package com.evolve.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "Guide")
@JsonIgnoreProperties(value = {"guide_id"},
        allowGetters = true)
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guide_id")
    private int guide_id;
    @Column(name = "creator_id")
    private int creator_id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "city")
    private String city;
    @Column(name = "budget")
    private Integer budget;
    @Column(name = "likes")
    private Integer likes;
    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private Collection<Location> locations;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "guide_hashtag",
            joinColumns = @JoinColumn(name = "guide_id", referencedColumnName = "guide_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "hashtag_id"))
    private Collection<HashTag> hashtags;
}
