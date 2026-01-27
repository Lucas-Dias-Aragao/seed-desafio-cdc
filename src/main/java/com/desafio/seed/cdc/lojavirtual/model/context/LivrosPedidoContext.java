package com.desafio.seed.cdc.lojavirtual.model.context;

import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class LivrosPedidoContext {

    private final Map<Integer, Livro> livrosPorId;

}
