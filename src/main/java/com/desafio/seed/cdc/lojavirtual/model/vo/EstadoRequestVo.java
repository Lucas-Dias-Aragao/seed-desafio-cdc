package com.desafio.seed.cdc.lojavirtual.model.vo;

import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.ExistsId;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.UniqueValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstadoRequestVo {

    private Integer id;

    @NotBlank(message = MessageConstants.NOME_OBRIGATORIO)
    @UniqueValue(domainClass = Estado.class, fieldName = "nome")
    private String nome;

    @NotNull(message = MessageConstants.PAIS_OBRIGATORIO)
    @ExistsId(domainClass = Pais.class, fieldName = "id")
    private Integer idPais;

}
