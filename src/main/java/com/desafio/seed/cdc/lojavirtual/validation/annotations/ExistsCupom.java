package com.desafio.seed.cdc.lojavirtual.validation.annotations;

import com.desafio.seed.cdc.lojavirtual.validation.validators.ValidCupomValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidCupomValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsCupom {

    String message() default "Cupom não encontrado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
