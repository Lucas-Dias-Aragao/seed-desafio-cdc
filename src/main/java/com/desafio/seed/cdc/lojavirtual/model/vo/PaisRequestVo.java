package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaisRequestDTO {

    @NotBlank(message = MessageConstants.NOME_OBRIGATORIO)
    @UniqueValue(domainClass = Pais.class, fieldName = "nome")
    private String nome;

}
