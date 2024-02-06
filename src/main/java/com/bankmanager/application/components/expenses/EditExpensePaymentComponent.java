package com.bankmanager.application.components.expenses;

import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.services.expenses.CashFlowService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.data.binder.Binder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;

public class EditExpensePaymentComponent extends CustomDialog {

    private final Binder<ValueModel> binder = new Binder<>();
    public EditExpensePaymentComponent(CashFlowService cashFlowService,  Runnable afterEdit) {
        super("Editar despesa", "Confirmar");

        var fieldCompetencyDate = new DatePicker("Data de competência");
        fieldCompetencyDate.setLocale(new Locale("pt", "BR"));
        binder.forField(fieldCompetencyDate)
                .withValidator(Objects::nonNull, "Informe uma data válida!")
                .bind(ValueModel::getCompetencyDate, ValueModel::setCompetencyDate);

        add(fieldCompetencyDate);

        addConfirmAction(() -> {
            if (binder.isValid()) {
                cashFlowService.update(this.binder.getBean().getIdCashFlow(), this.binder.getBean().getCompetencyDate());
                afterEdit.run();
                close();
                NotificationHelper.success("Despesa editada com sucesso!");
            }
        });
    }

    public void open(Long idCashFlow) {
        BinderHelper.setAndClearFields(new ValueModel(idCashFlow, LocalDate.now()), binder);
        super.open();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ValueModel {
        private Long idCashFlow;
        private LocalDate competencyDate;
    }
}
