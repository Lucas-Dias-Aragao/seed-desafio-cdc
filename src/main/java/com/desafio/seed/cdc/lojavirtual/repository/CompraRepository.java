package com.desafio.seed.cdc.lojavirtual.repository;

import com.desafio.seed.cdc.lojavirtual.model.dto.DetalheCompraResponse;
import com.desafio.seed.cdc.lojavirtual.model.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    @Query("SELECT NEW com.desafio.seed.cdc.lojavirtual.model.dto.DetalheCompraResponse("
            + "compra.nome, compra.sobrenome, compra.email, compra.telefone, compra.logradouro, "
            + "compra.numero, compra.cep, compra.bairro, compra.complemento, compra.cidade, "
            + "pais.nome, estado.nome, pedido.total, cupom.codigo, cupom.percentual) "
            + "FROM Compra compra "
            + "LEFT JOIN compra.pais pais "
            + "LEFT JOIN compra.estado estado "
            + "LEFT JOIN compra.pedido pedido "
            + "LEFT JOIN compra.cupom cupom "
            + "WHERE compra.id = :compraId")
    DetalheCompraResponse findDetalheCompraById(@Param("compraId") Integer compraId);

    // TODO: adicionar nomes livros e quantidades
    //private List<LivroDetalheCompraResponse> livro;
}
