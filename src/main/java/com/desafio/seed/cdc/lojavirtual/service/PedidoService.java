package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ItemPedidoService itemPedidoService;

    @Transactional
    public Pedido createPedido(final NovoPedidoRequestVo novoPedidoVo) {

        Pedido pedido = Pedido.builder().total(novoPedidoVo.getTotal()).build();
        pedido = pedidoRepository.saveAndFlush(pedido);

        itemPedidoService.createItensDoPedido(novoPedidoVo, pedido);

        return pedido;

    }
}
