package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class DetalheCompraResponse {

    @JsonIgnore
    private String nomeComprador;

    @JsonIgnore
    private String sobrenomeComprador;

    @Setter
    private String nomeCompletoComprador;

    private String emailComprador;

    private String telefoneComprador;

    private String enderecoCompleto;

    private BigDecimal total;

    @Setter
    private Boolean cupomAplicado;

    @Setter
    private BigDecimal valorFinalComDesconto;

    @JsonIgnore
    private BigDecimal percentualDesconto;

    @JsonIgnore
    private String codigoCupom;

    public DetalheCompraResponse(final String nomeComprador, final String sobrenomeComprador, final String emailComprador, final String telefoneComprador,
                                 final String logradouro, final String numero, final String cep, final String bairro, final String complemento, final String municipio,
                                 final String pais, final String estado, final BigDecimal total, final String codigoCupom, final BigDecimal percentualDesconto) {

        this.emailComprador = emailComprador;
        this.telefoneComprador = telefoneComprador;
        this.nomeComprador = nomeComprador;
        this.sobrenomeComprador = sobrenomeComprador;

        EnderecoResponse endereco = new EnderecoResponse(logradouro, numero, bairro, complemento, cep, municipio, estado, pais);
        this.enderecoCompleto = endereco.getEnderecoCompletoFormatado();

        this.codigoCupom = codigoCupom;
        this.total = total;
        this.percentualDesconto = percentualDesconto;
        //TODO: retornar nomes livros e quantidade (LivroDetalheCompraResponse)

    }
}
