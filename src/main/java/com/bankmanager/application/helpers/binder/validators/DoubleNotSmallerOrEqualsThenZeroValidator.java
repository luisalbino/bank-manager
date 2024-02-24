package com.bankmanager.application.helpers.binder.validators;

import com.bankmanager.application.helpers.ConvertHelper;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import jakarta.validation.Valid;

import java.util.Objects;

public class DoubleNotSmallerOrEqualsThenZeroValidator implements Validator<Double> {
    @Override
    public ValidationResult apply(Double aDouble, ValueContext valueContext) {
        if (ConvertHelper.toDouble(aDouble, 0D) <= 0) {
            return ValidationResult.error("Valor precisa ser maior que zero (0)!");
        }

        return ValidationResult.ok();
    }
}
