package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
