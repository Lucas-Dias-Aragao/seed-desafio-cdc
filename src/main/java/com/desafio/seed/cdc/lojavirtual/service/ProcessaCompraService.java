package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.context.PaisEstadoContext;
import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessaCompraService {

    private final PaisService paisService;

    private final PedidoService pedidoService;

    private final CompraService compraService;

    @Transactional
    public Integer processa(final NovaCompraRequestVo pedidoRequest) {

        PaisEstadoContext paisEstadoContext =
                paisService.validarEObterPaisEstado(pedidoRequest.getPaisId(), pedidoRequest.getEstadoId());

        Pedido pedido = pedidoService.createPedido(pedidoRequest.getPedido());
        Compra compra = compraService.createCompra(pedidoRequest, pedido, paisEstadoContext);
        return compra.getId();
    }

}
