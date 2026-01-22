package com.desafio.seed.cdc.lojavirtual.validation.validators;

import com.desafio.seed.cdc.lojavirtual.validation.annotations.CpfOuCnpj;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

import java.lang.annotation.Annotation;

public class CpfOuCnpjValidator implements ConstraintValidator<CpfOuCnpj, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isBlank()) {
            return false;
        }

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(new CPF() {
            @Override public Class<? extends Annotation> annotationType() { return CPF.class; }
            @Override public String message() { return ""; }
            @Override public Class<?>[] groups() { return new Class[0]; }
            @Override public Class<? extends Payload>[] payload() { return new Class[0]; }
        });

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(new CNPJ() {
            @Override public Class<? extends Annotation> annotationType() { return CNPJ.class; }
            @Override public String message() { return ""; }
            @Override public Class<?>[] groups() { return new Class[0]; }
            @Override public Class<? extends Payload>[] payload() { return new Class[0]; }

            @Override
            public Format format() {
                return null;
            }
        });

        return cpfValidator.isValid(value, null) || cnpjValidator.isValid(value, null);
    }

}
