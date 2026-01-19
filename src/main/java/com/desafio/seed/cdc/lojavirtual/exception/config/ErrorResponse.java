package com.desafio.seed.cdc.lojavirtual.exception.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private HttpStatus status;

    private String message;

    private Map<String, String> fieldErrors;

    private LocalDateTime timestamp;

    private Integer countErrors;

    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, HttpStatus status, Map<String, String> fieldErrors, Integer countErrors) {
        this.message = message;
        this.status = status;
        this.fieldErrors = fieldErrors;
        this.countErrors = countErrors;
    }
}
