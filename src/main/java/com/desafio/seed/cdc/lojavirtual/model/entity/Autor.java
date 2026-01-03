package com.desafio.seed.cdc.lojavirtual.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "lojavirtual_db", name = "TB_AUTOR")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "DATA_REGISTRO")
    private LocalDateTime dataRegistro;

}
