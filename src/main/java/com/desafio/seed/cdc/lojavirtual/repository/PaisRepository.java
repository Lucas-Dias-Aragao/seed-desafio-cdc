package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

    @Query("SELECT COUNT(estado) > 0 "
            + "FROM Pais pais "
            + "LEFT JOIN Estado estado "
            + "ON estado.pais.id = pais.id "
            + "WHERE pais.id = :paisId")
    Boolean existsEstadoByIdPais(@Param("paisId") final Integer paisId);

    @Query("SELECT COUNT(estado) > 0 "
            + "FROM Pais pais "
            + "LEFT JOIN Estado estado "
            + "ON estado.pais.id = pais.id "
            + "WHERE pais.id = :paisId "
            + "AND estado.id = :estadoId")
    Boolean existsEstadoIdAssocidoAoPais(@Param("paisId") Integer paisId, @Param("estadoId") Integer estadoId);
}
