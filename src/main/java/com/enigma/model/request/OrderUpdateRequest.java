package com.enigma.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderUpdateRequest {
    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String userId;

    private String campsiteId;
}
