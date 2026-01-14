package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.dto.CategoriaRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public ResponseEntity createCategoria(final CategoriaRequestDTO dto) {
        String nome = dto.getNome().toLowerCase();

        Boolean existsCategoriaNome = categoriaRepository.existsCategoriaByNome(nome);

        if(existsCategoriaNome) {
            throw new BusinessException("Já existe categoria cadastrada com esse nome", HttpStatus.BAD_REQUEST);
        }

        Categoria novaCategoria = dto.toModel();

        categoriaRepository.save(novaCategoria);
        return ResponseEntity.ok().build();
    }

}
