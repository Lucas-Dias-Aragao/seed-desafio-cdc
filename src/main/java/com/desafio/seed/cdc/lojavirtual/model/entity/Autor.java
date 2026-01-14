package com.desafio.seed.cdc.lojavirtual.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "lojavirtual_db", name = "TB_AUTOR")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DATA_REGISTRO")
    private LocalDateTime dataRegistro = LocalDateTime.now();

    public Autor(final String nome, final String email, final String descricao) {
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

}
