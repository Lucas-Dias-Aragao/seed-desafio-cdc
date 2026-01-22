package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.dto.PaisRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.repository.PaisRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaisService {

    private final PaisRepository paisRepository;

    @Transactional
    public ResponseEntity<?> createPais(final PaisRequestDTO dto) {

        Pais novoPais = new Pais(dto.getNome());

        paisRepository.save(novoPais);

        return ResponseEntity.ok().build();
    }

    public Pais getPaisExistente(final Integer idPais) {
        Optional<Pais> pais = paisRepository.findById(idPais);

        if(!pais.isPresent()) {
            throw new BusinessException("País não encontrado.", HttpStatus.BAD_REQUEST);
        }

        return pais.get();
    }
}
