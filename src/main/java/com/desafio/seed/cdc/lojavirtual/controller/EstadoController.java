package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.vo.EstadoRequestVo;
import com.desafio.seed.cdc.lojavirtual.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estado")
public class EstadoController {

    private final EstadoService estadoService;

    @PostMapping
    public ResponseEntity<?> createEstado(@Valid @RequestBody EstadoRequestVo dto) {
        return estadoService.createEstado(dto);
    }
}
