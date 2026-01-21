package com.desafio.seed.cdc.lojavirtual.model.vo;

import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import com.desafio.seed.cdc.lojavirtual.utils.MessageConstants;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.UniqueValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AutorRequestVo {

    @NotBlank(message = MessageConstants.NOME_OBRIGATORIO)
    private String nome;

    @Email
    @NotBlank(message = MessageConstants.EMAIL_OBRIGATORIO)
    @UniqueValue(domainClass = Autor.class, fieldName = "email")
    private String email;

    @NotBlank(message = MessageConstants.DESCRICAO_OBRIGATORIA)
    @Length(max = 400)
    private String descricao;

    public Autor toModel() {
        return new Autor(this.nome, this.email, this.descricao);
    }
}
