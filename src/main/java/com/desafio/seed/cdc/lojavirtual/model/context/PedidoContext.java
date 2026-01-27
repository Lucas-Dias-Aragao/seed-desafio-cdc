package com.desafio.seed.cdc.lojavirtual.model.context;

import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@AllArgsConstructor
public class PedidoContext {

    private final Map<Integer, Livro> livrosPorId;

    private final BigDecimal totalCalculado;
}
