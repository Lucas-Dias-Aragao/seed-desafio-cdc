package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.EstadoRequestDTO;
import com.desafio.seed.cdc.lojavirtual.service.EstadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EstadoController {

    private final EstadoService estadoService;

    @PostMapping("/estado")
    public ResponseEntity<?> createEstado(@Valid @RequestBody EstadoRequestDTO dto) {
        return estadoService.createEstado(dto);
    }
}
