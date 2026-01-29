package com.desafio.seed.cdc.lojavirtual.validation.validators;

import com.desafio.seed.cdc.lojavirtual.exception.BusinessException;
import com.desafio.seed.cdc.lojavirtual.validation.annotations.ExistsCupom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class ValidCupomValidator implements ConstraintValidator<ExistsCupom, String> {

    @Autowired
    private EntityManager manager;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        Query query = manager.createQuery("SELECT 1 FROM CupomDesconto cupom "
                + "WHERE cupom.codigo = :value AND cupom.validade >= :dataAtual");

        query.setParameter("value", value);
        query.setParameter("dataAtual", LocalDate.now());

        var result = query.getResultList();

        if(result.isEmpty()) {
           throw new BusinessException("Cupom inválido.", HttpStatus.BAD_REQUEST);
        }

        return true;
    }
}
