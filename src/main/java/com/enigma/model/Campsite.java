package com.enigma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    private BigDecimal price;


    @Column(nullable = true)
    private String file;

    @Column(nullable = true)
    private int likeCount;

    @OneToMany(mappedBy = "campsite", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Order> order;

}
