package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalheCompraResponse {

    private String nomeCompletoComprador;

    private String emailComprador;

    private String telefoneComprador;

    private String endereçoCompleto;

    private BigDecimal total;

    private List<LivroDetalheCompraResponse> livro = new ArrayList<>();

    public DetalheCompraResponse(final String nomeComprador, final String sobrenomeComprador, final String emailComprador, final String telefoneComprador,
                                 final String logradouro, final String numero, final String cep, final String bairro, final String complemento, final String municipio,
                                 final String pais, final String estado, final BigDecimal total) {

        this.nomeCompletoComprador = nomeComprador + " " + sobrenomeComprador;
        this.emailComprador = emailComprador;
        this.telefoneComprador = telefoneComprador;

        EnderecoResponse endereco = new EnderecoResponse(logradouro, numero, bairro, complemento, cep, municipio, estado, pais);
        this.endereçoCompleto = endereco.getEnderecoCompletoFormatado();

        this.total = total;

    }
}
