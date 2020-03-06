package com.decagon.queuepay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailException extends RuntimeException{
    public EmailException(String message) {
        super(message);
    }
}
