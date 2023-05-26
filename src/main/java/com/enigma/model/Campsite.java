package com.enigma.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "campsites")
@Getter
@Setter
public class Campsite {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String address;

    private String province;

    @Column(nullable = true)
    private String file;

    @OneToMany(mappedBy = "campsite", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Order> order;

    @OneToMany(mappedBy = "campsite")
    private List<Rating> ratings;
}
