package com.springboot.cashmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponse {
    // Getters and Setters
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

}
