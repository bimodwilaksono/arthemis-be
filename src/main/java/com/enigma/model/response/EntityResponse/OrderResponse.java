package com.enigma.model.response.EntityResponse;

import com.enigma.model.Campsite;
import com.enigma.model.Payment;
import com.enigma.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderResponse {
    private String id;
    private Boolean isCheckOut;

    private Boolean isCheckIn;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private User user;

    private Campsite campsite;

    private Payment payment;
}
