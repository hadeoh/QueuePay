package com.decagon.queuepay.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Response<T> {

    private HttpStatus status;
    private String message;
    private String error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime dateTime;
    private T data;
    private String debugMessage;
    private Map<String, String> subErrors;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public Map<String, String> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(Map<String, String> subErrors) {
        this.subErrors = subErrors;
    }

    private Response() {
        this.dateTime = LocalDateTime.now();
    }

    public Response(HttpStatus status) {
        this();
        this.status = status;
    }

    public Response(HttpStatus status, Throwable e) {
        this();
        this.status = status;
        this.error = "Unexpected error";
        this.debugMessage = e.getLocalizedMessage();
    }

    public Response(HttpStatus status, String message, Throwable e) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = e.getLocalizedMessage();
    }

    private void addSubError(String field, String message) {
        if (subErrors == null) {
            subErrors = new HashMap<>();
        }
        subErrors.put(field, message);
    }

    private void addValidationError(String field, String message) {
        addSubError(field, message);
    }

    private void addValidationError(FieldError fieldError) {
        this.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ObjectError objectError) {
        FieldError fieldError = (FieldError)  objectError;
        this.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    public void addValidationError(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationError);
    }

    private void addValidationError(ConstraintViolation<?> constraintViolation) {
        this.addValidationError(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(), constraintViolation.getMessage());
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }
}
