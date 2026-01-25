package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import com.desafio.seed.cdc.lojavirtual.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ItemPedidoService itemPedidoService;

    public Pedido createPedido(final NovoPedidoRequestVo request) {

        Pedido pedido = Pedido.builder().total(request.getTotal()).build();
        pedido = pedidoRepository.save(pedido);

        itemPedidoService.createItensDoPedido(request, pedido);

        return pedido;

    }
}
