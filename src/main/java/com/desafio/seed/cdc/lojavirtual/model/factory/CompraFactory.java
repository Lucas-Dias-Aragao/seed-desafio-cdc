package com.desafio.seed.cdc.lojavirtual.model.factory;

import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.CupomDescontoRepository;

public class CompraFactory {

    private final CupomDescontoRepository cupomDescontoRepository;

    private CompraFactory(CupomDescontoRepository cupomDescontoRepository) {
        this.cupomDescontoRepository = cupomDescontoRepository;
    }

    public static Compra create(final NovaCompraRequestVo request, final Pedido pedido, final Pais pais,
            final Estado estado) {

        return Compra.builder()
                .email(request.getEmail())
                .nome(request.getNome())
                .sobrenome(request.getSobrenome())
                .documento(request.getDocumento())
                .logradouro(request.getEndereco())
                .numero(request.getNumero())
                .cep(request.getCep())
                .bairro(request.getBairro())
                .complemento(request.getComplemento())
                .cidade(request.getCidade())
                .telefone(request.getTelefone())
                .pais(pais)
                .estado(estado)
                .pedido(pedido)
                .build();
    }

}
