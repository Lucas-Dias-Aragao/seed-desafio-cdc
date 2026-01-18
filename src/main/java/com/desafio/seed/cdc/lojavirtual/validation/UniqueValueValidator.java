package com.desafio.seed.cdc.lojavirtual.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String domainAttribute;

    private Class<?> klass;

    @Autowired
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("SELECT 1 FROM " + klass.getName() + " WHERE " + domainAttribute + " = :value");
        query.setParameter("value", value);

        var result = query.getResultList();

        if(!result.isEmpty()) {
            throw new IllegalStateException(value + " já está sendo utilizado, por favor, escolha outro " + domainAttribute);
        }

        return true;
    }

}
