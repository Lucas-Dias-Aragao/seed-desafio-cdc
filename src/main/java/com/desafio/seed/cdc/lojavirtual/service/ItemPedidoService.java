package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.entity.ItemPedido;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.factory.ItemPedidoFactory;
import com.desafio.seed.cdc.lojavirtual.model.vo.ItemRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.ItemPedidoRepository;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    private final LivroRepository livroRepository;

    public void createItensDoPedido(final NovoPedidoRequestVo request, final Pedido pedido) {
        List<Integer> idsLivros = request.getItens().stream()
                .map(ItemRequestVo::getIdLivro)
                .toList();

        Map<Integer, Livro> livros = livroRepository.findAllById(idsLivros)
                .stream()
                .collect(Collectors.toMap(Livro::getId, Function.identity()));

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
