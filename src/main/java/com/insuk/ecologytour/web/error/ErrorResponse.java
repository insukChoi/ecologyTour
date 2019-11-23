package com.insuk.ecologytour.web.error;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String error;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }
}
