package com.springboot.cashmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountResponse {
    // Getters and Setters
    private String message;

    public AccountResponse(String message) {
        this.message = message;
    }

}
