package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse {

    private Integer id;
    private String message;

    public SuccessResponse(final String message) {
        this.message = message;
    }

}
