package com.desafio.seed.cdc.lojavirtual.validation.annotations;

import com.desafio.seed.cdc.lojavirtual.validation.validators.UniqueValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueValueValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {

    String message() default "Informação já cadastrada anteriormente.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName ();

    Class<?> domainClass();

}
