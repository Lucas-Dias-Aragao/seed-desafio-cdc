package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.vo.PaisRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.repository.PaisRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
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
    public ResponseEntity<?> createPais(final PaisRequestVo dto) {

        Pais novoPais = new Pais(dto.getNome());

        paisRepository.save(novoPais);

        return ResponseEntity.ok().build();
    }

    public Pais getPaisExistente(final Integer idPais) {
        Optional<Pais> pais = paisRepository.findById(idPais);

        if(!pais.isPresent()) {
            throw new BusinessException(MessageConstants.PAIS_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
        }

        return pais.get();
    }

    public Boolean validaSePaisPossuiEstado(final Integer idPais) {
        boolean existsPais = paisRepository.existsById(idPais);

        if(!existsPais) {
            throw new BusinessException(MessageConstants.PAIS_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
        }

        return paisRepository.existsEstadoByIdPais(idPais);
    }
}
