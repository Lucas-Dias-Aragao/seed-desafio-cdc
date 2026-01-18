package com.desafio.seed.cdc.lojavirtual.exception.config;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationViolationException(MethodArgumentNotValidException ex) {
        List<FieldErrors> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream().map(e -> new FieldErrors(e.getField(), e.getDefaultMessage()))
                .toList();

        ErrorResponse response = new ErrorResponse("Erro de validação nos campos",HttpStatus.BAD_REQUEST, fieldErrors, fieldErrors.size());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMensagem());
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
