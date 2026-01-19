package com.desafio.seed.cdc.lojavirtual.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(schema = "lojavirtual_db", name = "TB_LIVRO")
public class Livro extends AbsctractEntity<Integer> {

    @Column(name = "TITULO", nullable = false, unique = true)
    private String titulo;

    @Column(name = "RESUMO", nullable = false)
    @Length(max = 500)
    private String resumo;

    @Column(name = "SUMARIO")
    private String sumario;

    @Column(name = "PRECO", nullable = false)
    @DecimalMin(value = "20.0", inclusive = true)
    private Double preco;

    @Column(name = "TOTAL_PAGINAS", nullable = false)
    @Min(value = 100)
    private Short qtdPaginas;

    @Column(name = "ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "DATA_PUBLICACAO")
    @Future
    private LocalDate dataPublicacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_AUTOR", nullable = false)
    private Autor autor;

}
