package com.desafio.seed.cdc.lojavirtual.exception.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldErrors> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> new FieldErrors(e.getField(), e.getDefaultMessage()))
                .toList();

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Erro de validação nos campos", fieldErrors);
        return ResponseEntity.badRequest().body(response);
    }

}
