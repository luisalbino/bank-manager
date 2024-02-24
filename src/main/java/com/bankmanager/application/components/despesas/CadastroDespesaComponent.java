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

    public CadastroDespesaComponent(DespesaService despesaService, Runnable afterSaveFunc) {
        super("Nova despesa");

        addConfirmAction(() -> {
            binder.validate();
            if (binder.isValid()) {
                despesaService.create(binder.getBean());
                afterSaveFunc.run();
                NotificationHelper.success("Despesa criada com sucesso!");
                close();
            }
        });

        var campoNome = new TextField("Nome");
        binder.forField(campoNome)
                .withValidator(new StringNotBlankValidator())
                .bind(DespesasEntity::getNome, DespesasEntity::setNome);

        var campoValor = new NumberField("Valor (Sugestão)");
        campoValor.setTooltipText("Valor sugerido ao pagar uma despesa!");
        binder.forField(campoValor)
                .withValidator(new ObjectNotNullValidador())
                .bind(DespesasEntity::getValor, DespesasEntity::setValor);

        var campoTipo = new ComboBox<TipoDespesaEnum>("Tipo de despesas");
        campoTipo.setItems(TipoDespesaEnum.values());
        campoTipo.setItemLabelGenerator(TipoDespesaEnum::getDescricao);
        binder.forField(campoTipo)
                .withValidator(new ObjectNotNullValidador())
                .bind(DespesasEntity::getTipo, DespesasEntity::setTipo);

        var layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.add(campoNome, campoValor, campoTipo);

        add(layout);
    }

    @Override
    public void open() {
        BinderHelper.setAndClearFields(new DespesasEntity(), binder);
        super.open();
    }
}
