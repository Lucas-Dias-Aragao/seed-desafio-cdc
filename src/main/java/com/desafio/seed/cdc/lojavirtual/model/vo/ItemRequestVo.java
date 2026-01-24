package com.desafio.seed.cdc.lojavirtual.model.vo;

import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.ExistsId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequestVo {

    @NotNull
    @ExistsId(domainClass = Livro.class, fieldName = "id")
    private Integer idLivro;

    @Positive(message = MessageConstants.QUANTIDADE_INVALIDA)
    private Short quantidade;

}
