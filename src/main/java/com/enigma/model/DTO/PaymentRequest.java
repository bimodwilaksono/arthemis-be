package com.enigma.model.DTO;

import com.enigma.model.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PaymentRequest {
    @NotBlank
    private String paymentMethod;
    @NotBlank
    private Integer amount;
    private String status;
    @NotBlank
    private Order order;
}
