package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.dto.LivroRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public ResponseEntity<?> createLivro(final LivroRequestDTO livroDTO) {

        Livro novoLivro = livroDTO.toModel();

        livroRepository.save(novoLivro);
        return ResponseEntity.ok().build();
    }
}
