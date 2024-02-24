package com.bankmanager.application.components.despesas.transacoes;

import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.entities.despesas.DespesasEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.ConvertHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.services.expenses.DespesaService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class CadastroTransacaoComponent extends CustomDialog {

    private final DespesasEntity despesa;
    private final Binder<ValueModel> binder = new Binder<>();

    public CadastroTransacaoComponent(DespesaService despesaService, DespesasEntity despesa, Runnable afterPay) {
        super("Pagar despesa", "Confirmar");

        this.despesa = despesa;

        var fieldPaymentDate = new DatePicker("Data do pagamento");
        fieldPaymentDate.setLocale(new Locale("pt", "BR"));
        binder.forField(fieldPaymentDate)
                .withValidator(Objects::nonNull, "Informe uma data válida!")
                .bind(ValueModel::getPaymentDate, ValueModel::setPaymentDate);

        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat("MM-yyyy");
        i18n.setReferenceDate(LocalDate.now());

        var fieldCompetencyDate = new DatePicker("Data de competência");
        fieldCompetencyDate.setI18n(i18n);
        fieldPaymentDate.setLocale(new Locale("pt", "BR"));
        binder.forField(fieldCompetencyDate)
                .withValidator(Objects::nonNull, "Informe uma data válida!")
                .bind(ValueModel::getCompetencyDate, ValueModel::setCompetencyDate);

        var fieldValue = new NumberField("Valor pago");
        binder.forField(fieldValue)
                .withValidator(value -> ConvertHelper.toDouble(value, 0D) > 0, "O valor precisa ser maior que zero (0)!")
                .bind(ValueModel::getValue, ValueModel::setValue);

        var layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        add(layout);
        layout.add(fieldPaymentDate, fieldCompetencyDate, fieldValue);

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
