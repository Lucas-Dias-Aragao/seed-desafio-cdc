package com.desafio.seed.cdc.lojavirtual.service;

import com.desafio.seed.cdc.lojavirtual.model.dto.UsuarioDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Usuario;
import com.desafio.seed.cdc.lojavirtual.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public ResponseEntity createUser(final UsuarioDTO dto) {
        Usuario usuario = criarUsuarioEntity(dto);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }

    private Usuario criarUsuarioEntity(final UsuarioDTO dto) {
        return Usuario.builder().nome(dto.getNome())
                .email(dto.getEmail())
                .descricao(dto.getDescricao())
                .dataRegistro(dto.getDataRegistro())
                .build();
    }
}
