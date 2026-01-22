package com.desafio.seed.cdc.lojavirtual.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutorDto {

    private String nome;

    private String descricao;

    public AutorDto(final String nome, final String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
