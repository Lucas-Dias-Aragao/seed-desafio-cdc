package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.DetalheCompraResponse;
import com.desafio.seed.cdc.lojavirtual.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detalhe-compra")
public class DetalheCompraController {

    private final CompraService compraService;

    @GetMapping("/{id}")
    public DetalheCompraResponse detalharCompra(@PathVariable("id") Integer idCompra) {
        return compraService.getDetalheCompra(idCompra);
    }

}
