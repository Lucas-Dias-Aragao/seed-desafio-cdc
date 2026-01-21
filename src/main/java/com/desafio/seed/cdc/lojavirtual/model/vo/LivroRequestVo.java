package com.desafio.seed.cdc.lojavirtual.model.vo;

import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.model.entity.Categoria;
import com.desafio.seed.cdc.lojavirtual.model.entity.Livro;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.ExistsId;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.UniqueValue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LivroRequestVo {

    @NotBlank(message = MessageConstants.TITULO_OBRIGATORIO)
    @UniqueValue(domainClass = Livro.class, fieldName = "titulo")
    private String titulo;

    @NotBlank(message = MessageConstants.RESUMO_OBRIGATORIO)
    @Length(max = 500, message = MessageConstants.TAMANHO_MAX_RESUMO_EXCEDIDO)
    private String resumo;

    private String sumario;

    @NotNull(message = MessageConstants.PRECO_OBRIGATORIO)
    @Min(value = 20, message = MessageConstants.VALOR_MINIMO_LIVRO)
    private BigDecimal preco;

    @NotNull(message = MessageConstants.QTD_PAGINAS_OBRIGATORIA)
    @Min(value = 100, message = MessageConstants.QTD_MINIMA_PAGINAS_LIVRO)
    private Short qtdPaginas;

    @NotBlank(message = MessageConstants.ISBN_OBRIGATORIO)
    @UniqueValue(domainClass = Livro.class, fieldName = "isbn")
    private String isbn;

    @NotNull(message = MessageConstants.DATA_NAO_INFORMADA)
    @Future(message = MessageConstants.DATA_DEVE_SER_FUTURO)
    private LocalDate dataPublicacao;

    @NotNull(message = MessageConstants.CATEGORIA_NAO_INFORMADA)
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Integer categoriaId;

    @NotNull(message = MessageConstants.AUTOR_NAO_INFORMADO)
    @ExistsId(domainClass = Autor.class, fieldName = "id")
    private Integer autorId;

    public Livro toModel() {
        return Livro.builder()
                .titulo(this.titulo)
                .resumo(this.resumo)
                .sumario(this.sumario)
                .preco(this.preco)
                .qtdPaginas(this.qtdPaginas)
                .isbn(this.isbn)
                .dataPublicacao(this.dataPublicacao)
                .categoria(Categoria.builder().id(this.categoriaId).build())
                .autor(Autor.builder().id(this.autorId).build())
                .build();
    }
}
