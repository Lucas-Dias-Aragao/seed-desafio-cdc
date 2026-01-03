package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.dto.AutorRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public ResponseEntity createUser(final AutorRequestDTO dto) {
        Autor autor = criarUsuarioEntity(dto);
        autorRepository.save(autor);
        return ResponseEntity.ok().build();
    }

    private Autor criarUsuarioEntity(final AutorRequestDTO dto) {
        return Autor.builder().nome(dto.getNome())
                .email(dto.getEmail())
                .descricao(dto.getDescricao())
                .dataRegistro(dto.getDataRegistro())
                .build();
    }
}
