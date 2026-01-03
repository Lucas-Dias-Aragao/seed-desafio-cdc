package com.desafio.seed.cdc.lojavirtual.model.dto;

import com.desafio.seed.cdc.lojavirtual.model.entity.Autor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AutorRequestDTO {

    @NotBlank(message = "O nome não pode ser nulo")
    private String nome;
    @Email
    @NotBlank(message = "O e-mail não pode ser nulo")
    private String email;
    @NotBlank(message = "A descrição não pode ser nula")
    @Length(max = 400)
    private String descricao;

    public Autor toModel() {
        return new Autor(this.nome, this.email, this.descricao);
    }
}
