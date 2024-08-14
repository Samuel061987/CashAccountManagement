package com.springboot.cashmanagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AccountDTO {
    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("currentBalance")
    double currentBalance;

    @JsonProperty("thresholdAmount")
    double thresholdAmount;

}
