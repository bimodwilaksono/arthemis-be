package com.enigma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "campsites")
@Getter
@Setter
public class Campsite {
    @Id
    private Integer id;

    private String name;

    private String location;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
}
