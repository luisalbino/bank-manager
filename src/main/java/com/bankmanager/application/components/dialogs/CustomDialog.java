package com.bankmanager.application.components.dialogs;

import com.bankmanager.application.components.buttons.CustomButton;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.HTMLHelper;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CustomDialog extends Dialog {

    private CustomButton confirmButton;

    public CustomDialog(String text) {
        this(text, null);
    }

    public CustomDialog(String text, String confirmText) {
        buildHeader(text);
        buildFooter(confirmText);
    }

    private void buildFooter(String confirmText) {
        var cancelButton = new CustomButton("Cancelar");
        cancelButton.setButtonError();
        cancelButton.addClickListener(event -> close());

        confirmButton = new CustomButton(ConvertHelper.toString(confirmText, "Salvar"));
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        getFooter().add(cancelButton, confirmButton);
    }

    public void addConfirmAction(Runnable confirmAction) {
        confirmButton.addClickListener(event -> confirmAction.run());
    }

    private void buildHeader(String text) {
        var layout = new VerticalLayout();
        layout.add(new H3(text));
        layout.add(HTMLHelper.getHR());
        getHeader().add(layout);
    }
}
