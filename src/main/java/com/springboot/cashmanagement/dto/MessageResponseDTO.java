package com.springboot.cashmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponseDTO {
    // Getters and Setters
    private String message;

    public MessageResponseDTO(String message) {
        this.message = message;
    }

}
