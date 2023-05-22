package com.enigma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Boolean isCheckOut;

    @Column(name = "checkin_date")
    private LocalDate checkInDate;

    @Column(name = "checkout_date")
    private LocalDate checkOutDate;

    //Many To Many with Customer
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //OneToOne with Campsite
    @ManyToOne
    @JoinColumn(name = "campsite_id", referencedColumnName = "id")
    @JsonBackReference
    private Campsite campsite;


//    //OneToOne with Payment
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;


}
