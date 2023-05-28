package com.enigma.model.request;

import com.enigma.utils.constants.PaymentMethod;
import com.enigma.utils.constants.PaymentStatus;
import com.enigma.utils.validator.AllowedCheckInDate;
import com.enigma.utils.validator.CheckInBeforeCheckOut;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@AllowedCheckInDate
@CheckInBeforeCheckOut
@Data
public class OrderRequest {
    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private String userId;

    private String campsiteId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull(message = "Amount should be filled")
    private Integer amount;

}
