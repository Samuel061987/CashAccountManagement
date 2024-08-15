package com.springboot.cashmanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AccountRequestDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "ThresholdAmount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private double thresholdAmount;
}
