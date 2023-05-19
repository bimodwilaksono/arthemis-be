package com.enigma.model.DTO;

import com.enigma.model.Campsite;
import com.enigma.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RatingRequest {
    @NotBlank
    private int score;
    private String Comment;
    @NotBlank
    private Campsite campsite;
    @NotBlank
    private User user;
}
