package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
}
