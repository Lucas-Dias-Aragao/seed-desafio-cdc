package com.desafio.seed.cdc.lojavirtual.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Table(schema = "lojavirtual_db", name = "TB_ITEM_PEDIDO")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_LIVRO", nullable = false)
    private Livro livro;

    @Column(name = "QUANTIDADE")
    private Short quantidade;

    @Column(name = "PRECO_MOMENTO")
    private BigDecimal precoMomentoDaCompra;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido pedido;

}
