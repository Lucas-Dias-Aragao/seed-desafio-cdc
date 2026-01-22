package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.PaisRequestDTO;
import com.desafio.seed.cdc.lojavirtual.service.PaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaisController {

    private final PaisService paisService;

    @PostMapping("/pais")
    public ResponseEntity<?> createPais(@Valid @RequestBody PaisRequestDTO dto) {
        return paisService.createPais(dto);
    }
}
