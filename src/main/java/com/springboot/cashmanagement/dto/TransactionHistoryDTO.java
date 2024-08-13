package com.springboot.cashmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory {

    @JsonProperty("transactionId")
    Long transactionId;

    @JsonProperty("type")
    String type;

    @JsonProperty("amount")
    double amount;

    @JsonProperty("creationDate")
    LocalDateTime creationDate;
}
