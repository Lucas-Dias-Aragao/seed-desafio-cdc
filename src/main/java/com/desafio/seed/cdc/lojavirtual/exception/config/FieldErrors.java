package com.desafio.seed.cdc.lojavirtual.exception.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FieldErrors {
    private String field;
    private String errorMessage;
}
