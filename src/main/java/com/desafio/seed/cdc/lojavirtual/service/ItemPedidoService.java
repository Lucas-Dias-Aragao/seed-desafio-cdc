package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.entity.ItemPedido;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.factory.ItemPedidoFactory;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    public void createItensDoPedido(final NovoPedidoRequestVo request, final Pedido pedido,
                                    final Map<Integer, Livro> livros) {

        List<ItemPedido> itens = request.getItens().stream()
                .map(item -> ItemPedidoFactory.create(
                        item,
                        livros.get(item.getIdLivro()),
                        pedido
                ))
                .toList();

        itemPedidoRepository.saveAll(itens);

    }
}
