package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.dto.LivroResponseDTO;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    @Query("SELECT NEW com.desafio.seed.cdc.lojavirtual.model.dto.LivroResponseDTO( "
            + "livro.id, livro.titulo) FROM Livro livro ORDER BY livro.id")
    List<LivroResponseDTO> listAllLivros();

    @Query("SELECT NEW com.desafio.seed.cdc.lojavirtual.model.dto.LivroResponseDTO( "
            + "livro.id, livro.titulo, livro.resumo, livro.sumario, livro.preco, livro.qtdPaginas, "
            + "livro.isbn, livro.dataPublicacao, autor.nome, autor.descricao) "
            + "FROM Livro livro "
            + "LEFT JOIN livro.autor autor "
            + "WHERE livro.id = :idLivro")
    LivroResponseDTO findDetalheLivroById(@Param("idLivro") final Integer idLivro);
}
