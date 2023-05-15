package com.enigma.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_tb")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
}
