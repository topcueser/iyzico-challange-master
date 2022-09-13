package com.iyzico.challenge.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private final HttpStatus httpStatus;

    private final LocalDateTime timestamp;

    private final String message;

    private final List<String> details;

    public ErrorResponse(HttpStatus httpStatus, String message, List<String> details) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}

