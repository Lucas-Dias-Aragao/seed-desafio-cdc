package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.dto.LivroRequestDTO;
import com.desafio.seed.cdc.lojavirtual.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<?> cadastraNovoLivro(@Valid @RequestBody LivroRequestDTO livroDto) {
        return livroService.createLivro(livroDto);
    }
}
