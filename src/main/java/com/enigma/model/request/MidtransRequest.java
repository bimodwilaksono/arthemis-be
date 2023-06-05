package com.enigma.model.request;

import com.enigma.model.TransactionsDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MidtransRequest {

    @JsonProperty("transaction_details")
    private TransactionsDetails transactionsDetails;
}
