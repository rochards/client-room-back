package com.rochards.saladereuniao.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data @AllArgsConstructor
public class ErrorDetails {

    private OffsetDateTime timestamp;
    private String message;
    private String details;
}
