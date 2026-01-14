package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import jakarta.validation.constraints.Past;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT COUNT(categoria) > 0 " //
            + "FROM Categoria categoria WHERE LOWER(categoria.nome) = LOWER(:nomeCategoria)")
    Boolean existsCategoriaByNome(@Param("nomeCategoria") final String nomeCategoria);
}
