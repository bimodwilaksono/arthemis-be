package com.enigma.model.request;

import com.enigma.model.Order;
import com.enigma.utils.constants.PaymentMethod;
import com.enigma.utils.constants.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentRequest {
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @NotBlank
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
