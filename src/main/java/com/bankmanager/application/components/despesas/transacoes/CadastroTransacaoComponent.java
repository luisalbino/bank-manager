package com.bankmanager.application.components.despesas.transacoes;

import com.bankmanager.application.components.dialogs.CustomDialog;
import com.bankmanager.application.entities.despesas.DespesaEntity;
import com.bankmanager.application.helpers.BinderHelper;
import com.bankmanager.application.helpers.NotificationHelper;
import com.bankmanager.application.helpers.binder.validators.DoubleNotSmallerOrEqualsThenZeroValidator;
import com.bankmanager.application.helpers.binder.validators.ObjectNotNullValidador;
import com.bankmanager.application.models.despesas.transacoes.CardTrasacaoModel;
import com.bankmanager.application.models.despesas.transacoes.TrasacaoModel;
import com.bankmanager.application.services.expenses.CardTrasacaoService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

import java.time.LocalDate;
import java.util.Locale;

public class CadastroTransacaoComponent extends CustomDialog {

    private final DespesaEntity despesa;
    private final Binder<CardTrasacaoModel> binder = new Binder<>();

    public CadastroTransacaoComponent(CardTrasacaoService cardTrasacaoService, DespesaEntity despesa, Runnable afterPay) {
        super("Pagar despesa", "Confirmar");

        this.despesa = despesa;

        var campoDataPagamento = new DatePicker("Data do pagamento");
        campoDataPagamento.setLocale(new Locale("pt", "BR"));
        binder.forField(campoDataPagamento)
                .withValidator(new ObjectNotNullValidador())
                .bind(CardTrasacaoModel::getDataPagamento, CardTrasacaoModel::setDataPagamento);

        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setDateFormat(despesa.isAnual() ? "yyyy" : "MM/yyyy");
        i18n.setReferenceDate(LocalDate.now());

        var campoDataCompetencia = new DatePicker("Data de competência");
        campoDataCompetencia.setI18n(i18n);
        campoDataCompetencia.setVisible(despesa.isNotRecorrente());
        campoDataCompetencia.setLocale(new Locale("pt", "BR"));
        binder.forField(campoDataCompetencia)
                .withValidator(new ObjectNotNullValidador())
                .bind(CardTrasacaoModel::getDataReferencia, CardTrasacaoModel::setDataReferencia);

        var campoValor = new NumberField("Valor pago");
        campoValor.setPrefixComponent(VaadinIcon.DOLLAR.create());
        binder.forField(campoValor)
                .withValidator(new DoubleNotSmallerOrEqualsThenZeroValidator())
                .bind(CardTrasacaoModel::getValor, CardTrasacaoModel::setValor);

        var layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.add(campoDataPagamento, campoDataCompetencia, campoValor);

        add(layout);

        addConfirmAction(() -> {
            if (binder.isValid()) {
                cardTrasacaoService.pagarOrEditar(despesa, binder.getBean());
                afterPay.run();
                close();
                NotificationHelper.success("Despesa paga/editada com sucesso!");
            }
        });
    }

    public void novo() {
        BinderHelper.setAndClearFields(new CardTrasacaoModel(despesa), binder);
        super.open();
    }

    public void editar(TrasacaoModel trasacaoModel) {
        BinderHelper.setAndClearFields(new CardTrasacaoModel(trasacaoModel), binder);
        super.open();
    }
}
