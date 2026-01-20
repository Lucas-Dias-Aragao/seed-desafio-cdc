package com.desafio.seed.cdc.lojavirtual.validation.validators;

import com.desafio.seed.cdc.lojavirtual.validation.annotations.ExistsId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {

    private String domainAttribute;

    private Class<?> klass;

    @Autowired
    private EntityManager manager;

    @Override
    public void initialize(ExistsId params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("SELECT id FROM " + klass.getName() + " WHERE " + domainAttribute + " = :value");
        query.setParameter("value", value);

        var result = query.getResultList();

        if(result.isEmpty()) {
            throw new IllegalStateException("Não foi encontrado " +klass.getSimpleName()+ " com o " + domainAttribute + " informado.");
        }

        return true;
    }
}
