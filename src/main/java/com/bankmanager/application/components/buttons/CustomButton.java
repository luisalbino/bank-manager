package com.bankmanager.application.components.buttons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

        this.getStyle().set("cursor", "pointer");
    }

    public void setButtonError() {
        this.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }
}
