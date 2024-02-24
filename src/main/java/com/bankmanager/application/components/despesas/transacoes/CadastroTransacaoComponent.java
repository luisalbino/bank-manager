package com.bankmanager.application.components.despesas.transacoes;

import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.helpers.binder.validators.DoubleNotSmallerOrEqualsThenZeroValidator;
import com.bankmanager.application.helpers.binder.validators.ObjectNotNullValidador;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;

public class CadastroTransacaoComponent extends CustomDialog {

    private final DespesaEntity despesa;
    private final Binder<ValueModel> binder = new Binder<>();

    public CadastroTransacaoComponent(DespesaService despesaService, DespesaEntity despesa, Runnable afterPay) {
        super("Pagar despesa", "Confirmar");

        this.despesa = despesa;

        var campoDataPagamento = new DatePicker("Data do pagamento");
        campoDataPagamento.setLocale(new Locale("pt", "BR"));
        binder.forField(campoDataPagamento)
                .withValidator(new ObjectNotNullValidador())
                .bind(ValueModel::getPaymentDate, ValueModel::setPaymentDate);

        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat(despesa.isAnual() ? "yyyy" : "MM/yyyy");
        i18n.setReferenceDate(LocalDate.now());

        var campoDataCompetencia = new DatePicker("Data de competência");
        campoDataCompetencia.setI18n(i18n);
        campoDataCompetencia.setVisible(despesa.isNotRecorrente());
        campoDataCompetencia.setLocale(new Locale("pt", "BR"));
        binder.forField(campoDataCompetencia)
                .withValidator(new ObjectNotNullValidador())
                .bind(ValueModel::getCompetencyDate, ValueModel::setCompetencyDate);

        var campoValor = new NumberField("Valor pago");
        campoValor.setPrefixComponent(VaadinIcon.DOLLAR.create());
        binder.forField(campoValor)
                .withValidator(new DoubleNotSmallerOrEqualsThenZeroValidator())
                .bind(ValueModel::getValue, ValueModel::setValue);

        var layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.add(campoDataPagamento, campoDataCompetencia, campoValor);

        add(layout);

        addConfirmAction(() -> {
            if (binder.isValid()) {
                despesaService.pay(despesa, binder.getBean().getPaymentDate(), binder.getBean().getCompetencyDate(), binder.getBean().getValue());
                afterPay.run();
                close();
                NotificationHelper.success("Despesa paga com sucesso!");
            }
        });
    }

    @Override
    public void open() {
        BinderHelper.setAndClearFields(new ValueModel(ConvertHelper.toDouble(despesa.getValor(), 0D),  LocalDate.now(), LocalDate.now()), binder);
        super.open();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ValueModel {
        private Double value;
        private LocalDate paymentDate;
        private LocalDate competencyDate;
    }
}
