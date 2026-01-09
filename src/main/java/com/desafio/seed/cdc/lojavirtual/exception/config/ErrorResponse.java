package com.desafio.seed.cdc.lojavirtual.exception.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private List<FieldErrors> fieldErrorsList;

}
