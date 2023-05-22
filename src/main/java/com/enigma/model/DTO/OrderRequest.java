package com.enigma.model.DTO;

import com.enigma.model.Campsite;
import com.enigma.model.Payment;
import com.enigma.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderRequest {
    private String id;

    private Boolean isCheckOut;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String userId;

    private String campsiteId;

    private Payment payment;
}
