package com.desafio.seed.cdc.lojavirtual.controller;

import com.desafio.seed.cdc.lojavirtual.model.vo.LivroRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.dto.LivroResponseDTO;
import com.desafio.seed.cdc.lojavirtual.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<?> cadastraNovoLivro(@Valid @RequestBody LivroRequestVo livroDto) {
        return livroService.createLivro(livroDto);
    }

    @GetMapping
    public List<LivroResponseDTO> listAllLivros() {
        return livroService.listAllLivros();
    }

    @GetMapping("/{idLivro}")
    public LivroResponseDTO findLivroById(@PathVariable("idLivro") final Integer idLivro) {
        return livroService.findLivroById(idLivro);
    }
}
