package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessaPedidoService {

    private final PaisService paisService;

    public String processa(final NovoPedidoRequestVo pedidoRequest) {
        validateDadosDoPedido(pedidoRequest);

        return "Processando pedido...";
    }

    private void validateDadosDoPedido(final NovoPedidoRequestVo pedidoRequest) {
        paisService.validaRelacaoPaisEEstado(pedidoRequest.getPaisId(), pedidoRequest.getEstadoId());
    }
}
