package com.springboot.cashmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponseDTO {
    // Getters and Setters
    private Object data;

    public ApiResponseDTO(Object data) {
        this.data = data;
    }

}
