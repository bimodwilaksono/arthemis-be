package com.enigma.model.request;

import lombok.Data;

@Data
public class OrderStatusUpdateRequest {
    private Boolean isCheckIn;
    private Boolean isCheckOut;
}

