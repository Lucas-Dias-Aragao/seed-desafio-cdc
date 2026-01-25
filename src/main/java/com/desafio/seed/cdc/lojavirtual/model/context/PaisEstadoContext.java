package com.desafio.seed.cdc.lojavirtual.model.context;

import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaisEstadoContext {

    private final Pais pais;

    private final Estado estado;
}
