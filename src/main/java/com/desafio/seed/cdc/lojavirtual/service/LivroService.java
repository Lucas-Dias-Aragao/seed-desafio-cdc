package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.model.vo.LivroRequestVo;
import com.desafio.seed.cdc.lojavirtual.model.dto.LivroResponseDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.repository.LivroRepository;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public LivroResponseDTO findDetalhesLivroById(final Integer idLivro) {

        LivroResponseDTO livro = livroRepository.findDetalheLivroById(idLivro);

        if(Objects.isNull(livro)) {
            throw new BusinessException(MessageConstants.LIVRO_NAO_ENCONTRADO, HttpStatus.NOT_FOUND);
        }

        return livro;
    }

}
