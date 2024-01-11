package com.bankmanager.application.components.dialogs;

import com.bankmanager.application.helpers.HTMLHelper;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CustomDialog extends Dialog {

    public CustomDialog(String text) {
        buildHeader(text);
    }

    private void buildHeader(String text) {
        var layout = new VerticalLayout();
        layout.add(new H3(text));
        layout.add(HTMLHelper.getHR());
        getHeader().add(layout);
    }
}
