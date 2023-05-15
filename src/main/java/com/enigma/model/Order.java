package com.enigma.model;

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
    private Integer id;

    private String status;

    @Column(name = "checkin_date")
    private LocalDate checkInDate;

    @Column(name = "checkout_date")
    private LocalDate checkOutDate;

    //Many To Many with Customer
    @ManyToOne
    private User user;

    //OneToOne with Campsite
    @OneToOne
    @JoinColumn(name = "campsite_id", referencedColumnName = "id")
    private Campsite campsite;


    //OneToOne with Payment
//    @OneToOne
//    private Payment payment


}
