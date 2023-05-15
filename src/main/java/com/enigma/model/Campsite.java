package com.enigma.model;

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
    private Integer id;

    private String name;

    private String location;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;

    @OneToMany(mappedBy = "campsite")
    private List<Rating> ratings;
}
