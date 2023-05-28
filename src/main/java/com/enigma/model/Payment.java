package com.enigma.model;

import com.enigma.utils.constants.PaymentMethod;
import com.enigma.utils.constants.PaymentStatus;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "amount")
    @NotNull(message = "Amount should be filled")
    private Integer amount;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
