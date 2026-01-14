package com.desafio.seed.cdc.lojavirtual.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BusinessException extends RuntimeException {
    private String mensagem;
    private HttpStatus status;
    private LocalDateTime timestamp;
    public BusinessException(String mensagem, HttpStatus status) {
        this.mensagem = mensagem;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
