package com.example.demo.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;

    public ErrorResponse(
            LocalDateTime timestamp,
            Integer status,
            String error) {

        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public Integer getStatus() {
        return status;
    }
    public String getError() {
        return error;
    }
}