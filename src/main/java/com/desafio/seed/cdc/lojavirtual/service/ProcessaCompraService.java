package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.context.PaisEstadoContext;
import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
        //TODO: validar total do pedido com soma dos itens
        PaisEstadoContext paisEstadoContext =
                paisService.validarEObterPaisEstado(pedidoRequest.getPaisId(), pedidoRequest.getEstadoId());

        Pedido pedido = pedidoService.createPedido(pedidoRequest.getPedido());
        Compra compra = compraService.createCompra(pedidoRequest, pedido, paisEstadoContext);
        return compra.getId();
    }

}
