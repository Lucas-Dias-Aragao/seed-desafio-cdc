package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.vo.NovoPedidoRequestVo;
import com.desafio.seed.cdc.lojavirtual.service.ProcessaPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/processa-pedido")
public class ProcessaPedidoController {

    private final ProcessaPedidoService processaPedidoService;

    @PostMapping
    public String processaPedidoInicial(@Valid @RequestBody NovoPedidoRequestVo vo) {
        return processaPedidoService.processa(vo);
    }

}
