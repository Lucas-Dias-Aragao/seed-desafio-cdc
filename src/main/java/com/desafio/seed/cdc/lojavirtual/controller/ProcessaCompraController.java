package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.vo.NovaCompraRequestVo;
import com.desafio.seed.cdc.lojavirtual.service.ProcessaCompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/processa-pedido")
public class ProcessaCompraController {

    private final ProcessaCompraService processaCompraService;
    private final Environment environment;

    @PostMapping
    public ResponseEntity<?> processaCompraInicial(@Valid @RequestBody NovaCompraRequestVo vo) throws URISyntaxException {
        Integer idCompra = processaCompraService.processa(vo);

        String baseUrl = environment.getProperty("app.url.base");
        String pedidoPath = environment.getProperty("app.url.pedido");
        URI uri = new URI(baseUrl + pedidoPath + idCompra);

        String compraRealizada = "Compra efetuada: " + idCompra + "\nDetalhes: " + uri;
        return ResponseEntity.created(uri).body(compraRealizada);
    }

}
