package com.desafio.seed.cdc.lojavirtual.model.factory;

import com.desafio.seed.cdc.lojavirtual.model.entity.ItemPedido;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.ItemRequestVo;

public class ItemPedidoFactory {

    private ItemPedidoFactory() {}

    public static ItemPedido create(final ItemRequestVo item, final Livro livro, final Pedido pedido) {

        return ItemPedido.builder()
                .livro(livro)
                .pedido(pedido)
                .quantidade(item.getQuantidade())
                .precoMomentoDaCompra(livro.getPreco())
                .build();
    }
}
