package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
