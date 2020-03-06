package com.decagon.queuepay.exception;

public class EmailExceptionResponse {
    private String email;

    public EmailExceptionResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
