package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pedido;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
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
    public String processa(final NovaCompraRequestVo pedidoRequest) {
        validateDadosDoPedido(pedidoRequest);

        Pedido pedido = createPedido(pedidoRequest);

        createCompra(pedidoRequest, pedido);

        return "Pedido " + pedido.getId() + " realizado com sucesso";
    }

    private Pedido createPedido(final NovaCompraRequestVo pedidoRequest) {
        NovoPedidoRequestVo vo = new NovoPedidoRequestVo(pedidoRequest.getPedido().getTotal(),
                pedidoRequest.getPedido().getItens());

        return pedidoService.createPedido(vo);
    }

    private Integer createCompra(final NovaCompraRequestVo novaCompraVo, final Pedido pedido) {
        Compra novaCompra = compraService.createCompra(novaCompraVo, pedido);
        return novaCompra.getId();
    }

    private void validateDadosDoPedido(final NovaCompraRequestVo pedidoRequest) {
       paisService.validaRelacaoPaisEEstado(pedidoRequest.getPaisId(), pedidoRequest.getEstadoId());
    }
}
