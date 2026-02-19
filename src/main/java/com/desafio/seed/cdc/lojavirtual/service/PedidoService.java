package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.context.LivrosPedidoContext;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.ItemRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import com.desafio.seed.cdc.lojavirtual.repository.PedidoRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final ItemPedidoService itemPedidoService;

    private final PedidoRepository pedidoRepository;

    private final LivroRepository livroRepository;

    public Pedido createPedido(final NovoPedidoRequestVo request) {

        LivrosPedidoContext livrosPedidoContext = validaValorTotalPedido(request);

        Pedido pedido = Pedido.builder().total(request.getTotal()).build();
        pedido = pedidoRepository.save(pedido);

        itemPedidoService.createItensDoPedido(request, pedido, livrosPedidoContext.getLivrosPorId());

        return pedido;

    }

    private LivrosPedidoContext validaValorTotalPedido(final NovoPedidoRequestVo pedido) {

        List<Integer> idsLivros = pedido.getItens().stream().map(ItemRequestVo::getIdLivro).toList();

        Map<Integer, Livro> livros = livroRepository.findAllById(idsLivros)
                .stream().collect(Collectors.toMap(Livro::getId, Function.identity()));

        BigDecimal totalCalculado = pedido.getItens().stream().map(item -> {
                    Livro livro = livros.get(item.getIdLivro());

                    return livro.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCalculado.compareTo(pedido.getTotal()) != 0) {
            throw new BusinessException(MessageConstants.VALOR_TOTAL_INVALIDO, HttpStatus.BAD_REQUEST);
        }

        return new LivrosPedidoContext(livros);
    }

}
