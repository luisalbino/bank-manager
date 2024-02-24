package com.bankmanager.application.components.despesas;

import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.entities.despesas.DespesasEntity;
import com.bankmanager.application.enums.despesas.TipoDespesaEnum;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.helpers.binder.validators.ObjectNotNullValidador;
import com.bankmanager.application.helpers.binder.validators.StringNotBlankValidator;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CadastroDespesaComponent extends CustomDialog {

    private final Binder<DespesasEntity> binder = new Binder<>();

    public CadastroDespesaComponent(DespesaService despesaService, Runnable afterCreating) {
        super("Nova despesa");

        addConfirmAction(() -> {
            binder.validate();
            if (binder.isValid()) {
                despesaService.create(binder.getBean());
                afterCreating.run();
                NotificationHelper.success("Despesa criada com sucesso!");
                close();
            }
        });

        var campoNome = new TextField("Nome");
        binder.forField(campoNome)
                .withValidator(new StringNotBlankValidator())
                .bind(DespesasEntity::getNome, DespesasEntity::setNome);

        var campoValor = new NumberField("Valor (Sugestão)");
        campoValor.setTooltipText("Valor que será sugerido sempre que a despesa for paga!");
        binder.forField(campoValor)
                .withValidator(new ObjectNotNullValidador())
                .bind(DespesasEntity::getValor, DespesasEntity::setValor);

        var campoTipo = new ComboBox<TipoDespesaEnum>();
        binder.forField(campoTipo)
                .withValidator(new ObjectNotNullValidador())
                .bind(DespesasEntity::getTipo, DespesasEntity::setTipo);

        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(campoNome, campoValor);

        add(layout);
    }

    @Override
    public void open() {
        BinderHelper.setAndClearFields(new DespesasEntity(), binder);
        super.open();
    }
}
