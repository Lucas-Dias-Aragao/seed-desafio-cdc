package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

    @Query("""
        SELECT CASE
            WHEN NOT EXISTS (
                SELECT 1 FROM Estado e WHERE e.pais.id = :paisId
            )
            AND :estadoId IS NULL
            THEN true
            WHEN :estadoId IS NOT NULL AND EXISTS (
                SELECT 1 FROM Estado e WHERE e.id = :estadoId AND e.pais.id = :paisId
            )
            THEN true
            ELSE false
        END
    """)
    boolean estadoValidoParaPais(@Param("paisId") final Integer paisId, @Param("estadoId") final Integer estadoId);

}
