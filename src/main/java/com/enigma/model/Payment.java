package com.enigma.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Payment")
@Getter @Setter @NoArgsConstructor
public class Payment {
    @Id @Column(name = "id")
    private Integer id;
    @Column(name = "payment_method")
    @NotBlank(message = "Payment method should be filled")
    private String paymentMethod;
    @Column(name = "amount")
    @NotNull(message = "Amount should be filled")
    private Integer amount;
    @Column(name = "status")
    @NotBlank(message = "status should be filled")
    private String status;
}
