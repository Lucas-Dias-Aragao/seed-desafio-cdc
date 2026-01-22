package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.dto.EstadoRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.repository.EstadoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    private final PaisService paisService;


    public ResponseEntity<?> createEstado(@Valid EstadoRequestDTO dto) {
        Pais pais = paisService.getPaisExistente(dto.getIdPais());
        Estado novoEstado = new Estado(dto.getNome(), pais);

        estadoRepository.save(novoEstado);
        return ResponseEntity.ok().build();

    }
}
