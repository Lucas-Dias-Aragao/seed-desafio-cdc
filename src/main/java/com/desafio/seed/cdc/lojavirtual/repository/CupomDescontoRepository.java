package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.CupomDesconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Integer> {

    @Query("SELECT cupom FROM CupomDesconto cupom WHERE cupom.codigo = :codigoCupom")
    Optional<CupomDesconto> getCupomByCodigo(@Param("codigoCupom") final String codigoCupom);
}
