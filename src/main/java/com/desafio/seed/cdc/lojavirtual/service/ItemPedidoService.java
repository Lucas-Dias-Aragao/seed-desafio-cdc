package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.entity.ItemPedido;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.ItemRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.ItemPedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final LivroService livroService;

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedido createItemPedido(ItemRequestVo itemVo, Pedido pedido) {
        Livro livro = livroService.getLivroById(itemVo.getIdLivro());

        ItemPedido item = ItemPedido.builder()
                .livro(livro)
                .quantidade(itemVo.getQuantidade())
                .pedido(pedido)
                .precoMomentoDaCompra(livro.getPreco())
                .build();

        return itemPedidoRepository.save(item);

    }

    public void createItensDoPedido(final NovoPedidoRequestVo novoPedidoVo, final Pedido pedido) {
        novoPedidoVo.getItens().forEach(item -> createItemPedido(item, pedido));
    }
}
