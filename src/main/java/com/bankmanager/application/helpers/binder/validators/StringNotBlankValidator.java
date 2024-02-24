package com.bankmanager.application.helpers.binder.validators;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import org.apache.commons.lang3.StringUtils;

public class StringNotBlankValidator implements Validator<String> {

    @Override
    public ValidationResult apply(String string, ValueContext valueContext) {
        if (StringUtils.isBlank(string)) {
            return ValidationResult.error("Informe um valor válido!");
        }

        return ValidationResult.ok();
    }
}
