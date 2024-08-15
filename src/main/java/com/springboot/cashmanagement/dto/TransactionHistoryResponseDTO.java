package com.springboot.cashmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryResponseDTO {
    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("currentBalance")
    double currentBalance;

    @JsonProperty("thresholdAmount")
    double thresholdAmount;

    @JsonProperty("transactionHistory")
    List<TransactionHistoryDTO> transactionHistory;

}