package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaRequestDTO {

    @NotBlank(message = MessageConstants.NOME_OBRIGATORIO)
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome")
    private String nome;

}
