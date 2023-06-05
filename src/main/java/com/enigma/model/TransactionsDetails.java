package com.enigma.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsDetails {
    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("gross_amount")
    private Integer grossAmount;
}
