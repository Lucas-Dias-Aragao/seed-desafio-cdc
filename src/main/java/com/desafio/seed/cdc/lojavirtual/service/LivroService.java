package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.vo.LivroRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.dto.LivroResponseDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    @Transactional
    public ResponseEntity<?> createLivro(final LivroRequestVo livroDTO) {

        Livro novoLivro = livroDTO.toModel();

        livroRepository.save(novoLivro);
        return ResponseEntity.ok().build();
    }

    public List<LivroResponseDTO> listAllLivros() {
        return livroRepository.listAllLivros();
    }

    public LivroResponseDTO findLivroById(final Integer idLivro) {
        return null;
    }
}
