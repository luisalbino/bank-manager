package com.bankmanager.application.components.despesas;

import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.entities.despesas.DespesasEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class NovaDespesaComponent extends CustomDialog {

    private final Binder<DespesasEntity> binder = new Binder<>();

    public NovaDespesaComponent(DespesaService despesaService, Runnable afterCreating) {
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
                .withValidator(StringUtils::isNotBlank, "Informe um nome!")
                .bind(DespesasEntity::getNome, DespesasEntity::setNome);

        var campoValor = new NumberField("Valor (Sugestão)");
        binder.forField(campoValor)
                .withValidator(Objects::nonNull, "Informe uma sugestão de valor a ser pago!")
                .bind(DespesasEntity::getValor, DespesasEntity::setValor);

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
