package com.desafio.seed.cdc.lojavirtual.model.vo;

import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class NovoPedidoRequestVo {

    @NotNull(message = MessageConstants.VALOR_TOTAL_INVALIDO)
    @Positive(message = MessageConstants.VALOR_TOTAL_INVALIDO)
    private BigDecimal total;

    @Size(min = 1, message = MessageConstants.DEVE_HAVER_PELO_MENOS_UM_ITEM)
    @Valid
    private List<ItemRequestVo> itens = new ArrayList<>();

}
