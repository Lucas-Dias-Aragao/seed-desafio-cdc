package com.desafio.seed.cdc.lojavirtual.model.vo;

import com.desafio.seed.cdc.lojavirtual.model.entity.Estado;
import com.desafio.seed.cdc.lojavirtual.model.entity.Pais;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.CpfOuCnpj;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.ValidCupom;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.ExistsId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NovaCompraRequestVo {

    @NotBlank(message = MessageConstants.EMAIL_OBRIGATORIO)
    @Email(message = MessageConstants.EMAIL_INVALIDO)
    private String email;

    @NotBlank(message = MessageConstants.NOME_OBRIGATORIO)
    private String nome;

    @NotBlank(message = MessageConstants.SOBRENOME_OBRIGATORIO)
    private String sobrenome;

    @CpfOuCnpj
    private String documento;

    @NotBlank(message = MessageConstants.LOGRADOURO_OBRIGATORIO)
    private String endereco;

    private String numero;

    @NotBlank(message = MessageConstants.CEP_OBRIGATORIO)
    private String cep;

    private String bairro;

    @NotBlank(message = MessageConstants.COMPLEMENTO_OBRIGATORIO)
    private String complemento;

    @NotBlank(message = MessageConstants.MUNICIPIO_OBRIGATORIO)
    private String cidade;

    @NotNull(message = MessageConstants.PAIS_OBRIGATORIO)
    @ExistsId(domainClass = Pais.class, fieldName = "id")
    private Integer paisId;

    @ExistsId(domainClass = Estado.class, fieldName = "id")
    private Integer estadoId;

    @NotBlank(message = MessageConstants.TELEFONE_OBRIGATORIO)
    private String telefone;

    @Valid
    @NotNull
    private NovoPedidoRequestVo pedido;

    @ValidCupom
    private String codigoCupom;

}
