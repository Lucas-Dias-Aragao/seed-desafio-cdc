package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.service.ProcessaCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/processa-pedido")
public class ProcessaCompraController {

    private final ProcessaCompraService processaCompraService;

    @PostMapping
    public String processaCompraInicial(@Valid @RequestBody NovaCompraRequestVo vo) {
        return processaCompraService.processa(vo);
    }

}
