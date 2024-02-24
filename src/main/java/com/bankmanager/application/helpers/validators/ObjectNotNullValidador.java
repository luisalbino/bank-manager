package com.bankmanager.application.helpers.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;

import java.util.Objects;

public class ObjectNotNullValidador implements Validator<Object> {
    @Override
    public ValidationResult apply(Object entidade, ValueContext valueContext) {
        if (Objects.isNull(entidade)) {
            return ValidationResult.error("Informe um valor válido!");
        }

        return ValidationResult.ok();
    }
}
