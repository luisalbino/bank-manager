package com.bankmanager.application.helpers;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.Binder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BinderHelper {

    public static <T>  void setAndClearFields(T bean, Binder<T> binder) {
        binder.setBean(bean);
        binder.getFields().forEach(HasValue::clear);
        binder.refreshFields();
    }

    public static <T> T valueProviderPrimitiveType(T primitiveType) {
        return primitiveType;
    }

    public static <T> void setterProviderPrimitiveType(T primitiveType, T newValue) {
        primitiveType = newValue;
    }
}

