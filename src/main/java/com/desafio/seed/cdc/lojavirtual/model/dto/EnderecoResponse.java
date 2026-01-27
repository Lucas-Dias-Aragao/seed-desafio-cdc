package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponse {

    private String logradouro;

    private String numero;

    private String bairro;

    private String complemento;

    private String cep;

    private String municipio;

    private String estado;

    private String pais;

    @JsonIgnore
    private final String SEPARACAO = " - ";

    public EnderecoResponse(String logradouro, String numero, String bairro, String complemento, String cep, String municipio, String estado, String pais) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.municipio = municipio;
        this.estado = estado;
        this.pais = pais;
    }

    public String getEnderecoCompletoFormatado() {
        StringBuilder endereco = new StringBuilder(logradouro);
        endereco.append(", nº").append(numero).append(SEPARACAO);
        endereco.append(complemento).append(SEPARACAO);
        endereco.append(bairro).append(SEPARACAO);
        endereco.append(cep).append(SEPARACAO);
        endereco.append(municipio).append(SEPARACAO);
        if(estado != null) {
            endereco.append(estado).append(SEPARACAO);
        }
        endereco.append(pais);

        return endereco.toString();
    }

}
