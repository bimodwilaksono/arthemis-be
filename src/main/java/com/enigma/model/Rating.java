package com.enigma.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rating_tb")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private int score;
    private String Comment;
    @ManyToOne
    @JoinColumn(name = "campsite_id")
    private Campsite campsite;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
