package com.enigma.model;

import jakarta.persistence.*;
<<<<<<< Updated upstream
import jakarta.validation.constraints.Email;
=======
>>>>>>> Stashed changes
import lombok.Data;

@Entity
@Table(name = "user_tb")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
}
