package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivroResponseDTO {

    private Integer id;

    private String titulo;

    private String resumo;

    private String sumario;

    private BigDecimal preco;

    private Short qtdPaginas;

    private String isbn;

    private LocalDate dataPublicacao;

    private AutorDto autor;

    public LivroResponseDTO(final Integer id, final String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public LivroResponseDTO(final Integer id, final String titulo, final String resumo, final String sumario, final BigDecimal preco,
                            final Short qtdPaginas, final String isbn, final LocalDate dataPublicacao, final String nomeAutor, final String descricaoAutor) {

        this.id = id;
        this.dataPublicacao = dataPublicacao;
        this.isbn = isbn;
        this.qtdPaginas = qtdPaginas;
        this.preco = preco;
        this.sumario = sumario;
        this.resumo = resumo;
        this.titulo = titulo;
        this.autor = new AutorDto(nomeAutor, descricaoAutor);
    }

}
