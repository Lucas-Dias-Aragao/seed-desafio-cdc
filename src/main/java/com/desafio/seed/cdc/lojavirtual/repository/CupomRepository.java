package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CupomRepository extends JpaRepository<CupomDesconto, Integer> {
}
