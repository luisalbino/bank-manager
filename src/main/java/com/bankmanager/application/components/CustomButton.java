package com.bankmanager.application.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;

public class CustomButton extends Button {

    public CustomButton(Icon icon) {
        this(null, icon);
    }

    public CustomButton(String text) {
        this(text, null);
    }

    public CustomButton(String text, Icon icon) {
        this.setText(text);
        this.setIcon(icon);
        
        getStyle().set("cursor", "pointer");
    }
}
