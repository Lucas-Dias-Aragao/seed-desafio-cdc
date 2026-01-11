package com.desafio.seed.cdc.lojavirtual.exception.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private List<FieldErrors> fieldErrorsList;
    private LocalDateTime timestamp;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, HttpStatus status, List<FieldErrors> fieldErrorsList) {
        this.message = message;
        this.status = status;
        this.fieldErrorsList = fieldErrorsList;
    }
}
