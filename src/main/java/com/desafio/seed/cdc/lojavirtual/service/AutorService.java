package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.dto.AutorRequestDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public ResponseEntity createUser(final AutorRequestDTO dto) throws BusinessException {
        validateAutorRequest(dto);

        Autor autor = dto.toModel();
        autorRepository.save(autor);
        return ResponseEntity.ok().build();
    }

    private void validateAutorRequest(final AutorRequestDTO dto) throws BusinessException{
        boolean existsEmail = autorRepository.existsByEmail(dto.getEmail());
        if(existsEmail) {
            throw new BusinessException("Já existe autor cadastrado com esse e-mail", HttpStatus.BAD_REQUEST);
        }
    }
}
