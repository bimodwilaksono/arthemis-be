package com.enigma.model.DTO;

import com.enigma.model.Campsite;
import com.enigma.model.Payment;
import com.enigma.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRequest {
    @NotBlank
    private LocalDate checkInDate;
    @NotBlank
    private LocalDate checkOutDate;
    @NotBlank
    private User user;
    @NotBlank
    private Campsite campsite;
    @NotBlank
    private Payment payment;
}
