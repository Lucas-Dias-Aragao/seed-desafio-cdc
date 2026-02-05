package com.desafio.seed.cdc.lojavirtual.model.vo;

import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.UniqueValue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class CadastroCupomRequestVo {

    @NotBlank(message = MessageConstants.INFORME_CODIGO_CUPOM)
    @UniqueValue(domainClass = CupomDesconto.class, fieldName = "codigo")
    private String codigo;

    @NotNull(message = MessageConstants.PERCENTUAL_OBRIGATORIO)
    @Positive
    private BigDecimal percentual;

    @Future(message = MessageConstants.DATA_DEVE_SER_FUTURO)
    private LocalDate validade;

}
