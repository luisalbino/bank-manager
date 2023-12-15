package com.bankmanager.application.helpers;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;

public class HTMLHelper {

    public static Component getHR() {
        return new Html("<hr/>");
    }
}
