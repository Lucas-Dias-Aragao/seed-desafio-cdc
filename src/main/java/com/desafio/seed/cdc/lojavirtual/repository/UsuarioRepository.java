package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
