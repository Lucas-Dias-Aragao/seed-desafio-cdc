package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.dto.CategoriaRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.repository.CategoriaRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Transactional
    public ResponseEntity<?> createCategoria(final CategoriaRequestDTO dto) {
        validateNomeCategoria(dto.getNome());

        Categoria novaCategoria = new Categoria(dto.getNome());

        categoriaRepository.save(novaCategoria);
        return ResponseEntity.ok().build();
    }

    private void validateNomeCategoria(final String nome) {
        if (Objects.isNull(nome) || nome.isBlank()) {
            throw new BusinessException(MessageConstants.NOME_OBRIGATORIO, HttpStatus.BAD_REQUEST);
        }
    }

}
