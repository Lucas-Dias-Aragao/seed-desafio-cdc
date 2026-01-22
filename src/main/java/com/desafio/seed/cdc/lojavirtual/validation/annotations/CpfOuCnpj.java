package com.desafio.seed.cdc.lojavirtual.validation.annotations;

import com.desafio.seed.cdc.lojavirtual.validation.validators.CpfOuCnpjValidator;
import com.desafio.seed.cdc.lojavirtual.validation.validators.ExistsIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CpfOuCnpjValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfOuCnpj {

    String message() default "O documento informado não é um CPF ou um CNPJ válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
