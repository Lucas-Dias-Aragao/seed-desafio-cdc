package com.desafio.seed.cdc.lojavirtual.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
@Table(schema = "lojavirtual_db", name = "TB_COMPRA")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "EMAIL_CLIENTE")
    private String email;

    @Column(name = "NOME_CLIENTE")
    private String nome;

    @Column(name = "SOBRENOME_CLIENTE")
    private String sobrenome;

    @Column(name = "DOCUMENTO_CLIENTE")
    private String documento;

    @Column(name = "LOGRADOURO")
    private String logradouro;

    @Column(name = "END_NUMERO")
    private String numero;

    @Column(name = "END_CEP")
    private String cep;

    @Column(name = "END_BAIRRO")
    private String bairro;

    @Column(name = "END_COMPLEMENTO")
    private String complemento;

    @Column(name = "END_MUNICIPIO")
    private String cidade;

    @ManyToOne
    @JoinColumn(name = "ID_PAIS", nullable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO", nullable = true)
    private Estado estado;

    @Column(name = "TELEFONE")
    private String telefone;

    @OneToOne
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido pedido;


}
