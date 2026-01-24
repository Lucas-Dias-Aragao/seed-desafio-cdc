package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.vo.EstadoRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.repository.EstadoRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    private final PaisService paisService;


    public ResponseEntity<?> createEstado(@Valid EstadoRequestVo dto) {
        Pais pais = paisService.getPaisExistente(dto.getIdPais());
        Estado novoEstado = new Estado(dto.getNome(), pais);

        estadoRepository.save(novoEstado);
        return ResponseEntity.ok().build();

    }

    public Estado getEstadoExistente(final Integer estadoId) {
        Optional<Estado> estado = estadoRepository.findById(estadoId);

            if(!estado.isPresent()) {
                throw new BusinessException(MessageConstants.ESTADO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
            }

            return estado.get();
        }

}
