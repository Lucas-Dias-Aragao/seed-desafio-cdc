package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.CategoriaRequestDTO;
import com.desafio.seed.cdc.lojavirtual.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
     public ResponseEntity cadastraNovaCategoria(@Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.createCategoria(dto);
    }
}
